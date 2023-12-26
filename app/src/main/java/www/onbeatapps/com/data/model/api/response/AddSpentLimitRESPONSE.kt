package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class AddSpentLimitRESPONSE(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("stripe_id")
    val customer: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("payment_id")
    val paymentId: String,
    @SerializedName("ephemeral_secret")
    val emepleralSecret: String,
):BaseResponse()