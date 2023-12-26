package www.onbeatapps.com

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import www.onbeatapps.com.utils.network.monitor.NetworkStateHolder.registerConnectivityMonitor
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by PKB on 05-05-2021.
 * pkb@gmail.com
 */

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //monitors network
        registerConnectivityMonitor()
        PaymentConfiguration.init(
            applicationContext,
//            "pk_test_TYooMQauvdEDq54NiTphI7jx"
//            "pk_test_51J29zESGMwg6PP2GvMgKqJ7y0aDnIbW7mUawDLOnk9cec6WyNAFHTJD3Kxd8adEJMlBA7ZdOZ8w2RqU8TuaDrsEP00640mGsTE"
        BuildConfig.STRIP_KEY
        )
    }

    companion object {
        var instance: www.onbeatapps.com.MyApplication? = null
            private set
    }
}