package yuyaprograming.jp.netagirekun

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_topic_set.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class TopicSetActivity : AppCompatActivity() {

    private var mHour = 0
    private var mMinute = 0

    private lateinit var nRealm: Realm

    private var mTopic: Topic? = null

    private fun showTimePickerDialog() {
        val d = Date() // 現在時刻
        val sdf = SimpleDateFormat("HH", Locale.getDefault())
        val c =sdf.format(d)
        val hourx : Int = Integer.parseInt(c)
        val dd = Date() // 現在時刻
        val sf = SimpleDateFormat("mm",Locale.getDefault())
        val b =sf.format(dd)
        val minutex : Int = Integer.parseInt(b)
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                mHour = hour
                mMinute = minute
            },
            hourx+9, minutex, true)
        timePickerDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_set)
        nRealm = Realm.getDefaultInstance()

        val z = mHour + mMinute
        val zz : Long = z.toLong()

        topic_set_button_above.setOnClickListener {
            if (zz == null) {
                topic_set_button_above.text = "ランダムで話題をセットする"
                val taskRealmResults = nRealm.where(Topic::class.java).findAll()
                if (taskRealmResults.size < 5) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("VALUE10", 10)
                    startActivity(intent)
                } else {
                    showTimePickerDialog()
                    val b = mutableListOf<String?>()
                    for (i in 1..5) {
                        var many = (0..taskRealmResults.size - 1).random()
                        var a = taskRealmResults[many]?.contents
                        while (b.contains(a)) {
                            many = (0..taskRealmResults.size - 1).random()
                            a = taskRealmResults[many]?.contents
                        }
                        b.add(a)
                    }
                    val resultIntent = Intent(applicationContext, TaskAlarmReceiver::class.java)
                    resultIntent.putExtra("topic_x", "b")
                    val resultPendingIntent = PendingIntent.getBroadcast(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, zz, resultPendingIntent)
                    Log.d("kotlintest", b.toString())
                }
            } else {
                topic_set_button_above.text = "設定した話題を解除する"
                val resultIntent = Intent(applicationContext, TaskAlarmReceiver::class.java)
                val resultPendingIntent = PendingIntent.getBroadcast(
                    this@TopicSetActivity,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(resultPendingIntent)
                Snackbar.make(findViewById(R.id.activity_topic_set), "セットした話題が削除されました", Snackbar.LENGTH_LONG)
                    .setAction("閉じる"){
                    }.show()
            }
        }

        topic_set_button_below.setOnClickListener {

        }
    }
}
