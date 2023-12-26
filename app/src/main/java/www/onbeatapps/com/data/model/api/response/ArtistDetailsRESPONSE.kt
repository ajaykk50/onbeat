package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ArtistDetailsRESPONSE(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Int
):BaseResponse() {
    data class Data(
        @SerializedName("artist_name")
        val artistName: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("genre")
        val genre: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("live")
        val live: Boolean,
        @SerializedName("other")
        val other: String,
        @SerializedName("performance")
        val performance: List<Performance>,
        @SerializedName("sound_cloud")
        val soundCloud: String,
        @SerializedName("spotify")
        val spotify: String,
        @SerializedName("bandcamp")
        val bandcamp: String,
        @SerializedName("stage")
        val stage: List<String>
    ) {
        data class Performance(
            @SerializedName("end_date")
            val endDate: String,
            @SerializedName("start_date")
            val startDate: String
        )
    }
}