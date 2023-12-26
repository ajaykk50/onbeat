package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class SendOtpRESPONSE(
    @SerializedName("otp")
    val otp: Int
//    @SerializedName("response")
//    val response: String
) : BaseResponse()