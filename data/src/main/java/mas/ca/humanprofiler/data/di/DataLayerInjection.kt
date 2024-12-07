package mas.ca.humanprofiler.data.di

import android.app.Application
import mas.ca.humanprofiler.data.repositories.ProfilesRepository
import mas.ca.humanprofiler.data.datasources.local.LocalStorageDatabase
import mas.ca.humanprofiler.data.network.ApiClient
import mas.ca.humanprofiler.domain.di.DataLayerInjectionInterface
import mas.ca.humanprofiler.domain.repository.ProfilesRepositoryInterface

class DataLayerInjection(application: Application) : DataLayerInjectionInterface {

    private val apiClient = ApiClient.getInstance(application)

    init {
        LocalStorageDatabase.create(application)
    }

    override fun provideProfileRepository(): ProfilesRepositoryInterface {
        return ProfilesRepository(
            agifyService = apiClient.agifyService,
            profileDao = LocalStorageDatabase.INSTANCE!!.profileDao
        )
    }

    companion object {

        @Volatile
        var INSTANCE: DataLayerInjection? = null
        fun getInstance(application: Application): DataLayerInjection {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataLayerInjection(application).also { INSTANCE = it }
            }
        }
    }
}