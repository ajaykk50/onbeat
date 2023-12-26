package www.onbeatapps.com.data.model.api.response


import com.google.gson.annotations.SerializedName

data class DownloadPDFRESPONSE(
    @SerializedName("status")
    val status: Int,
    @SerializedName("url")
    val url: String
):BaseResponse()