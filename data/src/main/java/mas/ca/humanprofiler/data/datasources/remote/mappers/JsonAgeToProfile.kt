package mas.ca.humanprofiler.data.datasources.remote.mappers

import mas.ca.humanprofiler.data.datasources.remote.entities.JsonAge
import mas.ca.humanprofiler.domain.entities.Age
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.utils.Mapper

object JsonAgeToProfile :Mapper<JsonAge, Profile> {
    override fun map(input: JsonAge): Profile {
        return Profile(
            name = Name(input.name),
            age = Age(input.age),
        )
    }
}