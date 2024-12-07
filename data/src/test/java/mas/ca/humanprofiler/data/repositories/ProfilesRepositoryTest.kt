package mas.ca.humanprofiler.data.repositories

import kotlinx.coroutines.runBlocking
import mas.ca.humanprofiler.data.datasources.local.ProfileDao
import mas.ca.humanprofiler.data.datasources.local.entities.LocalProfile
import mas.ca.humanprofiler.data.datasources.remote.AgifyService
import mas.ca.humanprofiler.data.datasources.remote.entities.JsonAge
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class ProfilesRepositoryTest {
    @Mock
    private lateinit var mockedAgifyService: AgifyService

    @Mock
    private lateinit var mockedProfileDao: ProfileDao

    private lateinit var profilesRepository: ProfilesRepository

    private val testName = "John Doe"
    private val testCachedProfile =
        LocalProfile(
            name = testName,
            age = 30,
            lastAccessMillis = System.currentTimeMillis(),
            cachedAtMillis = System.currentTimeMillis()
        )
    private val testJsonAge = JsonAge(name = testName, age = 30, count = 1)
    private val errorResponseBody = Response.error<Any>(422, "{}".toResponseBody("application/json".toMediaTypeOrNull()))

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        profilesRepository = ProfilesRepository(
            mockedAgifyService,
            mockedProfileDao
        )
    }

    @Test
    fun `Getting a profile for the first time is fetched from the remote endpoint`() = runBlocking {
        setupMocks(
            cachedProfile = null,
            apiResponse = testJsonAge
        )

        val result = profilesRepository.getProfileForName(Name(testName))

        verify(mockedAgifyService, times(1)).getAge(testName)
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).result.name.value, testName)
    }

    @Test
    fun `A cached profile is returned if it exists and it is not expired`() = runBlocking {
        setupMocks(cachedProfile = testCachedProfile)

        val result = profilesRepository.getProfileForName(Name(testName))

        verify(mockedAgifyService, times(0)).getAge(testName)
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).result.name.value, testName)
    }

    @Test
    fun `A cached profile that expired is removed and it is re-fetched`() = runBlocking {
        val expiredLocalProfile = testCachedProfile.copy(cachedAtMillis = 0)
        setupMocks(
            cachedProfile = expiredLocalProfile,
            apiResponse = testJsonAge
        )

        val result = profilesRepository.getProfileForName(Name(testName))

        verify(mockedProfileDao, times(1)).delete(expiredLocalProfile)
        verify(mockedAgifyService, times(1)).getAge(testName)
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).result.name.value, testName)
    }

    @Test
    fun `Errors are caught if the API returns an error`() = runBlocking {
        setupMocks(
            cachedProfile = null,
            apiError = HttpException(errorResponseBody)
        )

        val result = profilesRepository.getProfileForName(Name(testName))

        verify(mockedAgifyService, times(1)).getAge(testName)
        assertTrue(result is Result.Failure)
        assertEquals(GetProfileUseCase.ErrorType.INVALID_NAME, (result as Result.Failure).error)
    }

    private suspend fun setupMocks(
        cachedProfile: LocalProfile? = testCachedProfile,
        apiResponse: JsonAge? = testJsonAge,
        apiError: Exception? = null
    ) {
        doReturn(cachedProfile).`when`(mockedProfileDao).getByName(testName)
        if (apiError != null) {
            doThrow(apiError).`when`(mockedAgifyService).getAge(testName)
        } else {
            doReturn(apiResponse).`when`(mockedAgifyService).getAge(testName)
        }
    }
}