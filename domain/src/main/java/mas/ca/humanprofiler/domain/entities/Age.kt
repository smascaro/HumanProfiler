package mas.ca.humanprofiler.domain.entities

@JvmInline
value class Age(val value: Int) {
    init {
        if (value < MIN_AGE) throw IllegalArgumentException("Age cannot be negative")
    }

    companion object {
        const val MIN_AGE = 0
    }
}