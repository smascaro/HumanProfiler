package mas.ca.humanprofiler.data.network

import mas.ca.humanprofiler.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val API_URL = "https://api.agify.io"

    fun create(): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (Constants.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
        }
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}