package mas.ca.humanprofiler.domain.di

import mas.ca.humanprofiler.domain.use_cases.GetAllProfilesUseCase
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase

class DomainLayerInjection(private val dataLayerInjection: DataLayerInjectionInterface) {
    fun provideGetProfileUseCase(): GetProfileUseCase {
        return GetProfileUseCase(
            profilesRepository = dataLayerInjection.provideProfileRepository()
        )
    }

    fun provideGetAllProfilesUseCase(): GetAllProfilesUseCase {
        return GetAllProfilesUseCase(
            profilesRepository = dataLayerInjection.provideProfileRepository()
        )
    }

    companion object {

        @Volatile var INSTANCE: DomainLayerInjection? = null

        fun getInstance(dataLayerInjection: DataLayerInjectionInterface): DomainLayerInjection {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DomainLayerInjection(dataLayerInjection).also { INSTANCE = it }
            }
        }
    }
}