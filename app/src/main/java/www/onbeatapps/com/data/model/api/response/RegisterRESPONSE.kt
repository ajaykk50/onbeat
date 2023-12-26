package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class RegisterRESPONSE(
    @SerializedName("address")
    val address: Any,
    @SerializedName("anonymous")
    val anonymous: Boolean,
    @SerializedName("balances")
    val balances: Balances,
    @SerializedName("birthdate")
    val birthdate: Any,
    @SerializedName("city")
    val city: Any,
    @SerializedName("country")
    val country: Any,
    @SerializedName("email")
    val email: Any,
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
    val gtags: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: Any,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("money")
    val money: String,
    @SerializedName("orders")
    val orders: List<Any>,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("postcode")
    val postcode: Any,
    @SerializedName("refundable")
    val refundable: Boolean,
    @SerializedName("tickets")
    val tickets: List<Any>,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("virtual_money")
    val virtualMoney: String
):BaseResponse() {
    data class Balances(
        @SerializedName("173")
        val x173: Double,
        @SerializedName("174")
        val x174: Double,
        @SerializedName("229")
        val x229: Double,
        @SerializedName("230")
        val x230: Double
    )
}