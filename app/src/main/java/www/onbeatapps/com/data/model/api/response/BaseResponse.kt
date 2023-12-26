package www.onbeatapps.com.data.model.api.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("response")
    private val response: String? = null

    @SerializedName("message")
    val message: String? = null

    fun isSuccess(): Boolean {
        return response.equals("success", ignoreCase = true)
    }
}