package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class WristBandAddRESPONSE(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("autostage")
    val autostage: String,
//    @SerializedName("balances")
//    val balances: Balances,
    @SerializedName("banned")
    val banned: Boolean,
    @SerializedName("complete")
    val complete: Boolean,
    @SerializedName("consistent")
    val consistent: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("credits")
    val credits: String,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("event_id")
    val eventId: Int,
    @SerializedName("final_balance")
    val finalBalance: String,
//    @SerializedName("final_tokens_balance")
//    val finalTokensBalance: FinalTokensBalance,
    @SerializedName("final_virtual_balance")
    val finalVirtualBalance: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("operator")
    val `operator`: Boolean,
    @SerializedName("redeemed")
    val redeemed: Boolean,
//    @SerializedName("tag_uid")
//    val tagUid: String,
    @SerializedName("ticket_type_id")
    val ticketTypeId: Any,
//    @SerializedName("tokens")
//    val tokens: Tokens,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("virtual_credits")
    val virtualCredits: String
):BaseResponse()
//{
//    class Balances(
//    )
//
//    class FinalTokensBalance(
//    )
//
//    class Tokens(
//    )
//}