package www.onbeatapps.com.utils.network

import java.io.IOException

/**
 * Created by PKB on 01-06-2021.
 * PKB@gmail.com
 */
class NoInternetException : IOException() {
    override val message: String
        get() = "No network available, Please check your WiFi or Data connection."
}