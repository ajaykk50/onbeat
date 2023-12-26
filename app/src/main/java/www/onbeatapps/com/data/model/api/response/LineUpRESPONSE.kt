package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class LineUpRESPONSE(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Int
):BaseResponse() {
    data class Data(
        @SerializedName("artist_code")
        val artistCode: String,
        @SerializedName("artist_name")
        val artistName: String,
        @SerializedName("day")
        val day: String,
        @SerializedName("end_date")
        val endDate: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("live")
        val live: Boolean,
        @SerializedName("other")
        val other: String,
        @SerializedName("stage")
        val stage: String,
        @SerializedName("start_date")
        val startDate: String
    )
}