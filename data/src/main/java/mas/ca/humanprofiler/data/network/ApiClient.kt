package mas.ca.humanprofiler.data.network

import mas.ca.humanprofiler.data.datasources.remote.AgifyService

class ApiClient : ApiClientInterface {

    private val retrofit = RetrofitClient.create()

    override val agifyService: AgifyService by lazy {
        retrofit.create(AgifyService::class.java)
    }

    companion object {

        @Volatile
        private var INSTANCE: ApiClient? = null

        @Synchronized
        fun getInstance(): ApiClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiClient().also { INSTANCE = it }
            }
        }
    }
}