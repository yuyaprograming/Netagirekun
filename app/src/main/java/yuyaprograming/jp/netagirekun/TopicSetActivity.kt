package yuyaprograming.jp.netagirekun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_topic_set.*

class TopicSetActivity : AppCompatActivity() {
    private lateinit var nRealm: Realm

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
                Log.d("kotlintest", b.toString())
            }
        }

        topic_set_button_below.setOnClickListener {

        }
    }
}
