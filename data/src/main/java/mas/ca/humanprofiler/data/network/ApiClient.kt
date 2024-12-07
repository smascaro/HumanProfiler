package mas.ca.humanprofiler.data.network

import android.content.Context
import mas.ca.humanprofiler.data.datasources.remote.AgifyService

class ApiClient(private val applicationContext:Context) : ApiClientInterface {

    private val retrofit = RetrofitClient.create()

    override val agifyService: AgifyService by lazy {
        retrofit.create(AgifyService::class.java)
    }

    companion object {

        @Volatile
        private var INSTANCE: ApiClient? = null
        @Synchronized
        fun getInstance(applicationContext: Context): ApiClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiClient(applicationContext).also { INSTANCE = it }
            }
        }
    }
}