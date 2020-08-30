package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_topic_set.*
import kotlinx.android.synthetic.main.topic_detail_input.*

class TopicSetActivity : AppCompatActivity() {
    private lateinit var nRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_set)

        topic_set_button_above.setOnClickListener {
            val taskRealmResults = nRealm.where(Topic::class.java).findAll()
            val many = (0..taskRealmResults.size-1).random()
            val answer = taskRealmResults[many]
            Log.d("kotlintest", "answer")
        }

        topic_set_button_below.setOnClickListener {

        }
    }
}
