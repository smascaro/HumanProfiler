package mas.ca.humanprofiler.domain.di

import mas.ca.humanprofiler.domain.repository.ProfilesRepositoryInterface

interface DataLayerInjectionInterface {
    fun provideProfileRepository(): ProfilesRepositoryInterface
}