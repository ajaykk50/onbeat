package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class scanTicketRESPONSE(
    @SerializedName("data")
    val datas: Data,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("id")
    val id: Int,
):BaseResponse() {
    data class Data(
        @SerializedName("banned")
        val banned: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("credits")
        val credits: Int,
        @SerializedName("customer_id")
        val customerId: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("operator")
        val `operator`: Boolean,
        @SerializedName("purchaser_email")
        val purchaserEmail: Any,
        @SerializedName("purchaser_first_name")
        val purchaserFirstName: Any,
        @SerializedName("purchaser_last_name")
        val purchaserLastName: Any,
        @SerializedName("redeemed")
        val redeemed: Boolean,
        @SerializedName("ticket_type_id")
        val ticketTypeId: Int,
        @SerializedName("tokens_info")
        val tokensInfo: List<Any>,
        @SerializedName("virtual_credits")
        val virtualCredits: Int
    )
}