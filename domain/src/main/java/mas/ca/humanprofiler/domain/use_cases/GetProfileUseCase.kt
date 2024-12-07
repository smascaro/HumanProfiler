package mas.ca.humanprofiler.domain.use_cases

import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.repository.ProfilesRepositoryInterface

/**
 * Use case for retrieving a profile given their name.
 */
class GetProfileUseCase(
    private val profilesRepository: ProfilesRepositoryInterface
) : UseCase<GetProfileUseCase.Request, Result<Profile, GetProfileUseCase.ErrorType>> {

    override suspend fun execute(request: Request): Result<Profile, ErrorType> {
        return profilesRepository.getProfileForName(request.name)
    }

    data class Request(val name: Name) : UseCase.Request

    enum class ErrorType {
        NO_INTERNET,
        INVALID_NAME,
        UNKNOWN
    }
}