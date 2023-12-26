package www.onbeatapps.com.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import www.onbeatapps.com.R
import www.onbeatapps.com.data.local.prefs.AppPreferences
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity
import www.onbeatapps.com.ui.splash.SplashActivity
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var contentViewSmall: RemoteViews

    @Inject
    lateinit var preferences: AppPreferences
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // println("remoteMessage.rawData"+remoteMessage.rawData)
        // println("remoteMessage....."+remoteMessage.notification)
        // TODO(developer): Handle FCM messages here.
        val json = remoteMessage.data
        Log.e("NotificationRes", json.toString())
        sendNotification(json)

    }

    private fun sendNotification(messageBody: MutableMap<String, String>) {
        lateinit var intent: Intent
        if (isAppIsInBackground(this)) {
            if (preferences.getPages() != "splash" && preferences.getPages() != "login") {
                println("get page...." + preferences.getPages())
                intent = Intent(this, DashBoardActivity::class.java)
                EventBus.getDefault().post(NotificationEvent.Reload)
            } else intent = Intent(this, SplashActivity::class.java)
        } else {
//            intent = Intent(this, DashBoardActivity::class.java)
//            EventBus.getDefault().post(NotificationEvent.Reload)
             intent = Intent(this, SplashActivity::class.java)
        }


        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
        intent.putExtra("pkb", messageBody.get("key_value"))
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT,
//        )

        val pendingIntent = PendingIntent.getActivity(
            this, 0, /* Request code */ intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        contentViewSmall = RemoteViews(packageName, R.layout.custom_notification_small)
        contentViewSmall.setTextViewText(R.id.title, messageBody.get("title"))
        contentViewSmall.setTextViewText(R.id.text, messageBody.get("body"))
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.custom_notification_large)
        notificationLayoutExpanded.setTextViewText(R.id.title, messageBody.get("title"))
        notificationLayoutExpanded.setTextViewText(R.id.text, messageBody.get("body"))


        val channelId = "Customer"//getString("Customer")
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())



//           .setContentTitle(messageBody.get("title"))
//           .setContentText(messageBody.get("body"))

//        val channelId = "Customer"//getString("Customer")
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle(messageBody.get("title"))
//            .setContentText(messageBody.get("body"))
//            .setStyle(NotificationCompat.BigTextStyle())
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.drawable.ic_notification_trans)
//            notificationBuilder.setColor(resources.getColor(R.color.dark_button))
//        } else {
//            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
//        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        isInBackground = false
                    }
                }
            }
        }

        println("isInBackground...." + isInBackground)
        return !isInBackground
    }
}