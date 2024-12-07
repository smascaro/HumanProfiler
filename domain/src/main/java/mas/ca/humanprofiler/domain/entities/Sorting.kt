package mas.ca.humanprofiler.domain.entities

data class Sorting(val type: Type, val direction: Direction) {
    enum class Type {
        NAME,
        AGE,
        LAST_ACCESSED
    }

    enum class Direction {
        ASCENDING,
        DESCENDING;

        fun flip(): Direction {
            return when (this) {
                ASCENDING -> DESCENDING
                DESCENDING -> ASCENDING
            }
        }
    }

    companion object {
        val DEFAULT = Sorting(Type.LAST_ACCESSED, Direction.DESCENDING)
    }
}