package mas.ca.humanprofiler.data.datasources.local.mappers

import mas.ca.humanprofiler.data.datasources.local.entities.LocalProfile
import mas.ca.humanprofiler.domain.entities.Profile
import mas.ca.humanprofiler.domain.utils.Mapper

object ProfileToLocalProfile : Mapper<Profile, LocalProfile> {

    override fun map(input: Profile): LocalProfile {
        return LocalProfile(
            name = input.name.value,
            age = input.age.value,
            lastAccessMillis = input.lastAccessed.time,
        )
    }
}