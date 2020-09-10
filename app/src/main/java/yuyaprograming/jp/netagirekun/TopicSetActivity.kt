package yuyaprograming.jp.netagirekun

import android.app.AlarmManager
import android.app.DatePickerDialog
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
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0

    private lateinit var nRealm: Realm

    private var mTopic: Topic? = null

    private fun showDatePickerDialog() {
        val y = Date() // 現在時刻
        val yearx = SimpleDateFormat("yyyy", Locale.getDefault())
        val yy =yearx.format(y)
        val yearz : Int = Integer.parseInt(yy)
        val m = Date() // 現在時刻
        val monthx = SimpleDateFormat("MM",Locale.getDefault())
        val mm =monthx.format(m)
        val monthz : Int = Integer.parseInt(mm)
        val h = Date() // 現在時刻
        val dayx = SimpleDateFormat("dd",Locale.getDefault())
        val hh =dayx.format(h)
        val dayz : Int = Integer.parseInt(hh)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                mYear = year
                Log.d("A", mYear.toString())
                mMonth = month
                Log.d("A", mMonth.toString())
                mDay = dayOfMonth
                Log.d("A", mDay.toString())
            },
            yearz,
            monthz-1,
            dayz)
        datePickerDialog.show()

    }

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
                Log.d("A",mHour.toString())
                mMinute = minute
                Log.d("A",mMinute.toString())
                val taskRealmResults = nRealm.where(Topic::class.java).findAll()
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
                Log.d("otlintest",b.toString())
                val resultPendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, mYear)
                Log.d("kot", mYear.toString())
                calendar.set(Calendar.MONTH, mMonth)
                Log.d("kot", mMonth.toString())
                calendar.set(Calendar.DAY_OF_MONTH, mDay)
                Log.d("kot", mDay.toString())
                calendar.set(Calendar.HOUR, mHour)
                Log.d("kot", mHour.toString())
                calendar.set(Calendar.MINUTE, mMinute)
                Log.d("kot", mMinute.toString())
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, resultPendingIntent)
                Log.d("kotlins", calendar.toString())
                Snackbar.make(findViewById(R.id.topic_set_button_above), "話題は"+"$mYear"+"年"+"$mMonth"+1+"月"+"$mDay"+"日"+"$mHour"+ "時"+ "$mMinute"+ "分に設定されました", Snackbar.LENGTH_INDEFINITE)
                    .setAction("閉じる"){
                    }.show()
            },
            hourx+9, minutex, true)
        timePickerDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_set)
        nRealm = Realm.getDefaultInstance()

        topic_set_button_above.setOnClickListener {
            val taskRealmResults = nRealm.where(Topic::class.java).findAll()
            if (taskRealmResults.size < 5) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("VALUE10", 10)
                startActivity(intent)
            } else {
                showTimePickerDialog()
                showDatePickerDialog()
            }
        }

        topic_set_button_below.setOnClickListener {

        }
    }
}
