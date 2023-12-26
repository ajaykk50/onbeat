package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class EventInfoRESPONSE(
    @SerializedName("background")
    val background: String,
    @SerializedName("credit")
    val credit: Credit,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("event_serie")
    val eventSerie: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("name")
    val name: String,
//    @SerializedName("response")
//    val response: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("tokens")
    val tokens: List<Token>,
    @SerializedName("virtual_credit")
    val virtualCredit: VirtualCredit
):BaseResponse() {
    data class Credit(
        @SerializedName("id")
        val id: Int,
        @SerializedName("max_balance")
        val maxBalance: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("position")
        val position: Int,
        @SerializedName("spending_order")
        val spendingOrder: Int,
        @SerializedName("symbol")
        val symbol: String,
        @SerializedName("value")
        val value: String
    )

    data class Token(
        @SerializedName("id")
        val id: Int,
        @SerializedName("max_balance")
        val maxBalance: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("position")
        val position: Int,
        @SerializedName("spending_order")
        val spendingOrder: Int,
        @SerializedName("symbol")
        val symbol: String,
        @SerializedName("value")
        val value: String
    )

    data class VirtualCredit(
        @SerializedName("id")
        val id: Int,
        @SerializedName("max_balance")
        val maxBalance: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("position")
        val position: Int,
        @SerializedName("spending_order")
        val spendingOrder: Int,
        @SerializedName("symbol")
        val symbol: String,
        @SerializedName("value")
        val value: String
    )
}