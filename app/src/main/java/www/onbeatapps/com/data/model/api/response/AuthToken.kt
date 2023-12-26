package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("token")
    val token: String
):BaseResponse()