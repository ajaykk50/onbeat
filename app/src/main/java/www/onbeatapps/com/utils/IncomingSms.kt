package www.onbeatapps.com.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class IncomingSms : BroadcastReceiver() {
    // Get the object of SmsManager
    val sms: SmsManager = SmsManager.getDefault()
    override fun onReceive(mContext: Context?, intent: Intent) {
        // Retrieves a map of extended data from the intent.
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<Any>?
                for (i in pdusObj!!.indices) {
                    val currentMessage: SmsMessage =
                        SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                    val phoneNumber: String = currentMessage.displayOriginatingAddress
                    val message: String =
                        currentMessage.displayMessageBody.replace("\\D", "")

                    //message = message.substring(0, message.length()-1);
                    val myIntent = Intent("otpCode")
                    myIntent.putExtra("message", message)
                    myIntent.putExtra("number", phoneNumber)
                    LocalBroadcastManager.getInstance(mContext!!).sendBroadcast(myIntent)
                    // Show Alert
                } // end for loop
            } // bundle is null
        } catch (e: Exception) {
        }
    }
}