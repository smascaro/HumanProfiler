package mas.ca.humanprofiler.domain.repository

import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase

interface ProfilesRepositoryInterface {
    suspend fun getProfileForName(name: Name): Result<Profile, GetProfileUseCase.ErrorType>
    suspend fun getAllProfiles(): Result<List<Profile>, Unit>
}