package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class banGtagRESPONSE(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: String
)