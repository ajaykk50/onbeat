package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class EvenRESPONSE(
    @SerializedName("event_id")
    val eventId: Int,
    @SerializedName("event_name")
    val eventName: String,
    @SerializedName("android_app_version")
    val appVersion: String,
    @SerializedName("currency")
    val currency: String,
):BaseResponse()