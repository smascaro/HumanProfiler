package mas.ca.humanprofiler.domain.entities

import java.util.Date

data class Profile(
    val name: Name,
    val age: Age,
    val lastAccessed: Date = Date()
)