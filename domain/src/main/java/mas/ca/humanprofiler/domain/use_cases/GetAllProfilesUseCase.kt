package mas.ca.humanprofiler.domain.use_cases

import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.entities.Result
import mas.ca.humanprofiler.domain.entities.Sorting
import mas.ca.humanprofiler.domain.repository.ProfilesRepositoryInterface

/**
 * Use case for retrieving all previously requested profiles sorted by the given [Sorting].
 */
class GetAllProfilesUseCase(
    private val profilesRepository: ProfilesRepositoryInterface
) : UseCase<GetAllProfilesUseCase.Request, Result<List<Profile>, Unit>> {

    override suspend fun execute(request: Request): Result<List<Profile>, Unit> {
        return profilesRepository
            .getAllProfiles()
            .mapOnSuccess {
                it.sortedWith(getSortingComparator(request.sorting))
            }
    }

    private fun getSortingComparator(sorting: Sorting): Comparator<Profile> {
        return when (sorting.type) {
            Sorting.Type.NAME -> {
                when (sorting.direction) {
                    Sorting.Direction.ASCENDING -> compareBy { it.name.value }
                    Sorting.Direction.DESCENDING -> compareByDescending { it.name.value }
                }
            }

            Sorting.Type.AGE -> {
                when (sorting.direction) {
                    Sorting.Direction.ASCENDING -> compareBy { it.age.value }
                    Sorting.Direction.DESCENDING -> compareByDescending { it.age.value }
                }
            }

            Sorting.Type.LAST_ACCESSED -> {
                when (sorting.direction) {
                    Sorting.Direction.ASCENDING -> compareBy { it.lastAccessed }
                    Sorting.Direction.DESCENDING -> compareByDescending { it.lastAccessed }
                }
            }
        }
    }

    data class Request(val sorting: Sorting = Sorting.DEFAULT) : UseCase.Request

}