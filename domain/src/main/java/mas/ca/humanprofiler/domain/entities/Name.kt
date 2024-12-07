package mas.ca.humanprofiler.domain.entities

@JvmInline
value class Name(val value: String) {
    init {
        if (value.isEmpty()) throw IllegalArgumentException("Name cannot be empty")
    }
}