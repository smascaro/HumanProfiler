package mas.ca.humanprofiler.domain.use_cases

/**
 * A Use Case orchestrates the flow from and to the domain entities and apply business rules to fulfill
 * the goal of the business requirement.
 */
interface UseCase<in I : UseCase.Request, out O> {

    /**
     * @return The value created by the implementation.
     */
    suspend fun execute(request: I): O

    /**
     * Data passed to a request
     */
    interface Request
}