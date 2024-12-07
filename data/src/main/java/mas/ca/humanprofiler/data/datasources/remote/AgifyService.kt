package mas.ca.humanprofiler.data.datasources.remote

import mas.ca.humanprofiler.data.datasources.remote.entities.JsonAge
import retrofit2.http.GET
import retrofit2.http.Query

interface AgifyService {
    @GET("/")
    suspend fun getAge(@Query("name") name: String): JsonAge
}