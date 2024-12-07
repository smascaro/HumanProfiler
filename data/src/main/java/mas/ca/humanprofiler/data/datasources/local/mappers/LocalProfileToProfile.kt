package mas.ca.humanprofiler.data.datasources.local.mappers

import mas.ca.humanprofiler.data.datasources.local.entities.LocalProfile
import mas.ca.humanprofiler.domain.entities.Age
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.utils.Mapper
import java.util.Date

object LocalProfileToProfile : Mapper<LocalProfile, Profile> {
    override fun map(input: LocalProfile): Profile {
        return Profile(
            name = Name(input.name),
            age = Age(input.age),
            lastAccessed = Date(input.lastAccessMillis)
        )

    }
}