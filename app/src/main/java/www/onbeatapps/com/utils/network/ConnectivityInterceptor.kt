package www.onbeatapps.com.utils.network

import android.content.Intent
import android.os.CountDownTimer
import androidx.annotation.NonNull
import www.onbeatapps.com.utils.network.monitor.NetworkStateHolder
import okhttp3.Interceptor
import okhttp3.Response
import www.onbeatapps.com.MyApplication
import www.onbeatapps.com.data.local.prefs.AppPreferences
import www.onbeatapps.com.ui.noInternetActivity.noInternetActivity
import java.io.IOException

/**
 * Created by PKB on 02-06-2021.
 * PKB@gmail.com
 */
class ConnectivityInterceptor(private val appPreferences: AppPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        return if (!NetworkStateHolder.isConnected) {
            val i = Intent(MyApplication.instance, noInternetActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MyApplication.instance?.startActivity(i)
            throw NoInternetException()
        } else {
            var request = chain.request()

            if (appPreferences.getToken().isNotEmpty()&&appPreferences.getTokeCheck()) {
                request = request
                        .newBuilder()
                        .addHeader("Authorization", appPreferences.getToken())
                        .build()
            }
            chain.proceed(request)
        }
    }

}