package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ListCardRESPONSE(
    @SerializedName("card")
    val card: List<Card>
):BaseResponse() {
    data class Card(
        @SerializedName("card_type")
        val cardType: String,
        @SerializedName("last_four")
        val lastFour: String,
        @SerializedName("payment_method_id")
        val paymentMethodId: String
    )
}