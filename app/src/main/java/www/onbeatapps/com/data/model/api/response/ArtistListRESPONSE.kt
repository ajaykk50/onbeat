package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ArtistListRESPONSE(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Int
):BaseResponse() {
    data class Data(
        @SerializedName("artist_name")
        val artistName: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("artist_code")
        val artistCode: String,
        @SerializedName("live")
        val live: Boolean
    )
}