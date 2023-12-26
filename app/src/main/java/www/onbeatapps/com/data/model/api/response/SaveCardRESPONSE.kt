package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class SaveCardRESPONSE(
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("payment_id")
    val paymentId: String
//    @SerializedName("response")
//    val response: String
):BaseResponse()