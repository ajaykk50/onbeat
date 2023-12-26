package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class SaveCardStripRESPONSE(
    @SerializedName("clientSecret")
    val clientSecret: String,
    @SerializedName("created")
    val created: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isLiveMode")
    val isLiveMode: Boolean,
    @SerializedName("paymentMethod")
    val paymentMethod: PaymentMethod,
    @SerializedName("paymentMethodId")
    val paymentMethodId: String,
    @SerializedName("paymentMethodTypes")
    val paymentMethodTypes: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("usage")
    val usage: String
) {
    data class PaymentMethod(
        @SerializedName("billingDetails")
        val billingDetails: BillingDetails,
        @SerializedName("card")
        val card: Card,
        @SerializedName("created")
        val created: Int,
        @SerializedName("customerId")
        val customerId: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("liveMode")
        val liveMode: Boolean,
        @SerializedName("type")
        val type: String
    ) {
        data class BillingDetails(
            @SerializedName("address")
            val address: Address
        ) {
            data class Address(
                @SerializedName("postalCode")
                val postalCode: String
            )
        }

        data class Card(
            @SerializedName("brand")
            val brand: String,
            @SerializedName("checks")
            val checks: Checks,
            @SerializedName("country")
            val country: String,
            @SerializedName("expiryMonth")
            val expiryMonth: Int,
            @SerializedName("expiryYear")
            val expiryYear: Int,
            @SerializedName("funding")
            val funding: String,
            @SerializedName("last4")
            val last4: String,
            @SerializedName("networks")
            val networks: Networks,
            @SerializedName("threeDSecureUsage")
            val threeDSecureUsage: ThreeDSecureUsage
        ) {
            class Checks(
            )

            data class Networks(
                @SerializedName("available")
                val available: List<String>,
                @SerializedName("selectionMandatory")
                val selectionMandatory: Boolean
            )

            data class ThreeDSecureUsage(
                @SerializedName("isSupported")
                val isSupported: Boolean
            )
        }
    }
}