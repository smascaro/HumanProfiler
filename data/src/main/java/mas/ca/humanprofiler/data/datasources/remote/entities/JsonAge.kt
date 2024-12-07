package mas.ca.humanprofiler.data.datasources.remote.entities

import com.google.gson.annotations.SerializedName

data class JsonAge(
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("count") val count: Int
)
