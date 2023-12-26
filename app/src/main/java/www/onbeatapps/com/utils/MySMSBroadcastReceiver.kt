package www.onbeatapps.com.utils

import com.google.android.gms.common.api.CommonStatusCodes

import com.google.android.gms.auth.api.phone.SmsRetriever

import android.os.Bundle

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.Status


class MySMSBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {        // Get SMS message contents
                    val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                    val myIntent = Intent("otpCode")
                    myIntent.putExtra("message", message)
                    LocalBroadcastManager.getInstance(context!!).sendBroadcast(myIntent)
                }
                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }
    }
}