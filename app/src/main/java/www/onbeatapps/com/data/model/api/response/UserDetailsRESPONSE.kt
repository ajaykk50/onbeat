package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class UserDetailsRESPONSE(
    @SerializedName("address")
    val address: Any,
    @SerializedName("anonymous")
    val anonymous: Boolean,
    @SerializedName("birthdate")
    val birthdate: Any,
    @SerializedName("city")
    val city: Any,
    @SerializedName("country")
    val country: Any,
    @SerializedName("email")
    val email: String?=null,
    @SerializedName("registration_type")
    val registrationType: String? = null,
    @SerializedName("family_id")
    val familyId: Any,
    @SerializedName("family_owner")
    val familyOwner: Boolean,
    @SerializedName("first_name")
    val firstName: Any,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("global_refundable_money")
    val globalRefundableMoney: String,
    @SerializedName("gtags")
    val gtags: List<Gtag>,
    @SerializedName("card")
    val card: List<Cards>,
    @SerializedName("tickets")
    val ticket: List<Ticket>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("money")
    val money: String,
    @SerializedName("orders")
    val orders: List<Any>,
    @SerializedName("phone")
    val phone: String?= null,
    @SerializedName("postcode")
    val postcode: Any,
    @SerializedName("refundable")
    val refundable: Boolean,
    @SerializedName("virtual_money")
    val virtualMoney: String,
    @SerializedName("payment_id")
    val paymentId: String,
    @SerializedName("card_type")
    val cardType: String,
    @SerializedName("last_four")
    val cardNumber: String
) {
    data class Cards(
        @SerializedName("payment_method_id")
        val paymentMethodId: String,
        @SerializedName("card_type")
        val cardType: String,
        @SerializedName("last_four")
        val lastFour: String
    )

    data class Ticket(
        @SerializedName("banned")
        val banned: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("customer_id")
        val customerId: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("redeemed")
        val redeemed: Boolean,
        @SerializedName("type_name")
        val type_name: String,
        @SerializedName("ticket_type_id")
        val ticketTypeId: Int
    )

    data class Gtag(
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("banned")
        val banned: Boolean,
        @SerializedName("consistent")
        val consistent: Boolean,
        @SerializedName("credits")
        val credits: String,
        @SerializedName("customer_id")
        val customerId: Int,
        @SerializedName("final_balance")
        val finalBalance: String,
        @SerializedName("final_virtual_balance")
        val finalVirtualBalance: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("redeemed")
        val redeemed: Boolean,
        @SerializedName("tag_uid")
        val tagUid: String,
        @SerializedName("ticket_type_id")
        val ticketTypeId: Any,
        @SerializedName("virtual_credits")
        val virtualCredits: String

    )
}