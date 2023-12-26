package www.onbeatapps.com.utils.network

import androidx.annotation.NonNull
import www.onbeatapps.com.utils.network.monitor.NetworkStateHolder
import okhttp3.Interceptor
import okhttp3.Response
import www.onbeatapps.com.data.local.prefs.AppPreferences
import java.io.IOException

/**
 * Created by PKB on 02-06-2021.
 * PKB@gmail.com
 */
class ConnectivityInterceptorLogin(private val appPreferences: AppPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        return if (!NetworkStateHolder.isConnected) {
            throw NoInternetException()
        } else {
            var request = chain.request()
            if (appPreferences.getToken().isNotEmpty()) {
                request = request
                        .newBuilder()
                        .addHeader("Authorization", appPreferences.getToken())
                        .build()
            }
            chain.proceed(request)
        }
    }

}