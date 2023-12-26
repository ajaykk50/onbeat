package www.onbeatapps.com.utils.network.monitor

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.ContextCompat.startActivity
import www.onbeatapps.com.MyApplication
import www.onbeatapps.com.ui.noInternetActivity.noInternetActivity

class NetworkCallbackImp(private val holder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {
    var code: ArrayList<String> = arrayListOf()

    override fun onAvailable(network: Network) {
        code.add(network.hashCode().toString())
        if (!holder.isConnected){
            noInternetActivity.exit()
        }
        holder.isConnected = true
    }

    override fun onLost(network: Network) {
        while (code.remove(network.hashCode().toString()))
            if (code.isEmpty()) {
                holder.isConnected = false
                val i = Intent(MyApplication.instance,noInternetActivity::class.java)
                i.addFlags(FLAG_ACTIVITY_NEW_TASK)
                MyApplication.instance?.startActivity(i)
            }
    }
}