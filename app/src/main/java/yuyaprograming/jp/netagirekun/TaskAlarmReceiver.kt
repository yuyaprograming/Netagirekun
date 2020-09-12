package yuyaprograming.jp.netagirekun

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import io.realm.Realm

class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("kotlintest", "TaskAlarmReceiver.onReceive is called.")
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // SDKバージョンが26以上の場合、チャネルを設定する必要がある
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Channel description"
            notificationManager.createNotificationChannel(channel)
        }

        // 通知の設定を行う
        val builder = NotificationCompat.Builder(context, "default")
        builder.setSmallIcon(R.drawable.small_icon)
        builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.large_icon))
        builder.setWhen(System.currentTimeMillis())
        builder.setDefaults(Notification.DEFAULT_SOUND)
        builder.setAutoCancel(true)

        // EXTRA_TASK から Task の id を取得して、 id から Task のインスタンスを取得する
        val z = intent!!.getStringExtra("topic_x")
        val realm =Realm.getDefaultInstance()

        // タスクの情報を設定する
        builder.setTicker("話題をお届けしました")   // 5.0以降は表示されない
        builder.setContentTitle("<注目>")
        builder.setContentText("話題をお届けしました!!")

        // 通知をタップしたらアプリを起動するようにする
        val startAppIntent = Intent(context, TopicList::class.java)
        startAppIntent.putExtra("topic_list", z)
        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        val pendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, 0)
        builder.setContentIntent(pendingIntent)

        // 通知を表示する
        notificationManager.notify(1, builder.build())
        realm.close()
    }
}