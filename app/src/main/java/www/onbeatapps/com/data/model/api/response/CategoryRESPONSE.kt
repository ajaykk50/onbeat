package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName
data class CategoryRESPONSE(

    @SerializedName("categories")
    val categories: List<www.onbeatapps.com.data.model.api.response.CategoryRESPONSE.Category>,
//    @SerializedName("msg")
//    val msg: String,
//    @SerializedName("status")
//    val status: Boolean
) {
    data class Category(
        @SerializedName("products")
        val products: List<www.onbeatapps.com.data.model.api.response.CategoryRESPONSE.Category.Product>,
        @SerializedName("title")
        val title: String
    ) {
        data class Product(
            @SerializedName("description")
            val description: String,
            @SerializedName("imageUrl")
            val imageUrl: String,
            @SerializedName("price")
            val price: Int,
            @SerializedName("title")
            val title: String
        )
    }
}