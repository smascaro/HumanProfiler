package mas.ca.humanprofiler.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import mas.ca.humanprofiler.data.di.DataLayerInjection
import mas.ca.humanprofiler.domain.di.DomainLayerInjection
import mas.ca.humanprofiler.features.history.HistoryViewModel
import mas.ca.humanprofiler.features.input.InputViewModel

class UiLayerInjection(private val domainLayerInjection: DomainLayerInjection) {

    fun provideInputViewModelFactory(): ViewModelProvider.Factory {
        return InputViewModel.provideFactory(domainLayerInjection.provideGetProfileUseCase())
    }

    fun provideHistoryViewModelFactory(): ViewModelProvider.Factory {
        return HistoryViewModel.provideFactory(domainLayerInjection.provideGetAllProfilesUseCase())
    }

    companion object {
        @Volatile
        var INSTANCE: UiLayerInjection? = null

        fun getInstance(application: Application): UiLayerInjection {
            val dataLayerInjector = DataLayerInjection.getInstance(application)
            val domainLayerInjector = DomainLayerInjection.getInstance(dataLayerInjector)
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UiLayerInjection(domainLayerInjector).also { INSTANCE = it }
            }
        }
    }
}