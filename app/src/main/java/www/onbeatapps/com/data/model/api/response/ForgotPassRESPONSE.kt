package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ForgotPassRESPONSE(
    @SerializedName("otp")
    val otp: Int? = null
):BaseResponse()