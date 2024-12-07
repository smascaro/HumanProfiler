package mas.ca.humanprofiler.domain.utils

interface Mapper<I, O> {
    fun map(input: I): O
}