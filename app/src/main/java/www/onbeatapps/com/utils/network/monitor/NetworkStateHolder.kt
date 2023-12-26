package www.onbeatapps.com.utils.network.monitor

import android.app.Application
import android.content.Context
import android.net.*
import android.net.ConnectivityManager




object NetworkStateHolder : NetworkState {

    private lateinit var holder: NetworkStateImp

    override val isConnected: Boolean
        get() = holder.isConnected

    fun Application.registerConnectivityMonitor() {


        holder = NetworkStateImp()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(),
            NetworkCallbackImp(holder)
        )
    }

}