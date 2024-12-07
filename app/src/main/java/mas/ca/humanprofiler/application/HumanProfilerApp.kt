package mas.ca.humanprofiler.application

import android.app.Application
import mas.ca.humanprofiler.di.UiLayerInjection
import mas.ca.humanprofiler.utils.Constants
import timber.log.Timber

class HumanProfilerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        UiLayerInjection.getInstance(this)

        if (Constants.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}