package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class TrListResponse(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("currency")
    val currency: String,
//    @SerializedName("response")
//    val response: String,
    @SerializedName("total_spent")
    val totalSpent: Double,
    @SerializedName("transation")
    val transation: List<Transation>
):BaseResponse() {
    data class Transation(
        @SerializedName("access_direction")
        val accessDirection: Any,
        @SerializedName("action")
        val action: String,
        @SerializedName("catalog_item_id")
        val catalogItemId: Any,
        @SerializedName("catalog_item_type")
        val catalogItemType: Any,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("credit_amount")
        val creditAmount: String,
        @SerializedName("credit_id")
        val creditId: Int,
        @SerializedName("credit_name")
        val creditName: String,
        @SerializedName("credit_type")
        val creditType: String,
        @SerializedName("customer_gtag_id")
        val customerGtagId: Int,
        @SerializedName("customer_id")
        val customerId: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("device_id")
        val deviceId: Int,
        @SerializedName("event_id")
        val eventId: Int,
        @SerializedName("final_balance")
        val finalBalance: String,
        @SerializedName("gtag_counter")
        val gtagCounter: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("line_counter")
        val lineCounter: Int,
        @SerializedName("message")
        val message: Any,
        @SerializedName("monetary_quantity")
        val monetaryQuantity: Any,
        @SerializedName("monetary_total_price")
        val monetaryTotalPrice: Any,
        @SerializedName("monetary_unit_price")
        val monetaryUnitPrice: Any,
        @SerializedName("operation_id")
        val operationId: Int,
        @SerializedName("operator_gtag_id")
        val operatorGtagId: Int,
        @SerializedName("operator_id")
        val operatorId: Int,
        @SerializedName("order_id")
        val orderId: Any,
        @SerializedName("payment_method")
        val paymentMethod: String,
        @SerializedName("price")
        val price: Double,
        @SerializedName("priority")
        val priority: Any,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("products")
        val products: List<Any>,
        @SerializedName("sale_item_quantity")
        val saleItemQuantity: Int,
        @SerializedName("sale_item_total_price")
        val saleItemTotalPrice: Any,
        @SerializedName("sale_item_unit_price")
        val saleItemUnitPrice: String,
        @SerializedName("standard_total_price")
        val standardTotalPrice: String,
        @SerializedName("standard_unit_price")
        val standardUnitPrice: String,
        @SerializedName("station_id")
        val stationId: Int,
        @SerializedName("ticket_id")
        val ticketId: Any,
        @SerializedName("ticket_type_id")
        val ticketTypeId: Int,
        @SerializedName("user_flag_value")
        val userFlagValue: Any,
        @SerializedName("vendor_name")
        val vendorName: String
    )
}