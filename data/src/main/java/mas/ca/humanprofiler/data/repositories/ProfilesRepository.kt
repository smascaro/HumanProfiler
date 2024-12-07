package mas.ca.humanprofiler.data.repositories

import mas.ca.humanprofiler.data.datasources.local.ProfileDao
import mas.ca.humanprofiler.data.datasources.local.mappers.LocalProfileToProfile
import mas.ca.humanprofiler.data.datasources.local.mappers.ProfileToLocalProfile
import mas.ca.humanprofiler.data.datasources.remote.AgifyService
import mas.ca.humanprofiler.data.datasources.remote.mappers.JsonAgeToProfile
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.entities.failure
import mas.ca.humanprofiler.domain.entities.success
import mas.ca.humanprofiler.domain.repository.ProfilesRepositoryInterface
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase
import mas.ca.humanprofiler.utils.launchCoroutineBackground
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException
import java.util.Date

class ProfilesRepository(
    private val agifyService: AgifyService,
    private val profileDao: ProfileDao
) : ProfilesRepositoryInterface {

    override suspend fun getProfileForName(name: Name): Result<Profile, GetProfileUseCase.ErrorType> {
        return try {
            val cachedProfile = getCachedProfile(name)
            if (cachedProfile != null) {
                val updatedCachedProfile = cachedProfile.copy(lastAccessed = Date(System.currentTimeMillis()))
                profileDao.update(ProfileToLocalProfile.map(updatedCachedProfile))
                success(updatedCachedProfile)
            } else {
                val profile =
                    agifyService
                        .getAge(name.value)
                        .let { JsonAgeToProfile.map(it) }
                        .copy(lastAccessed = Date(System.currentTimeMillis()))

                launchCoroutineBackground {
                    profileDao.insert(ProfileToLocalProfile.map(profile))
                }
                success(profile)
            }
        } catch (unknownHostException: UnknownHostException) {
            Timber.e("Failure while fetching a profile age, no internet connection")
            failure(GetProfileUseCase.ErrorType.NO_INTERNET)
        } catch (httpException: HttpException) {
            Timber.e("Failure while fetching a profile age, http exception:", httpException)
            when (httpException.code()) {
                404, 422 -> failure(GetProfileUseCase.ErrorType.INVALID_NAME)
                else -> failure(GetProfileUseCase.ErrorType.UNKNOWN)
            }
        } catch (exception: Exception) {
            Timber.e("Failure while fetching a profile age", exception)
            failure(GetProfileUseCase.ErrorType.UNKNOWN)
        }
    }

    private suspend fun getCachedProfile(name: Name): Profile? {
        val cachedProfile = profileDao.getByName(name.value)
        return if (cachedProfile != null) {
            val isProfileCacheExpired = System.currentTimeMillis() - cachedProfile.cachedAtMillis > CACHE_EXPIRATION_TIME_MILLISECONDS
            if (isProfileCacheExpired) {
                profileDao.delete(cachedProfile)
                null
            } else {
                LocalProfileToProfile.map(cachedProfile)
            }
        } else {
            null
        }
    }

    override suspend fun getAllProfiles(): Result<List<Profile>, Unit> {
        val localProfiles = profileDao.getAll()
        return success(
            localProfiles.map { LocalProfileToProfile.map(it) }
        )
    }

    companion object {
        private const val CACHE_EXPIRATION_TIME_MILLISECONDS = 1000L * 60 * 60 * 24 * 30 // 30 days
    }
}