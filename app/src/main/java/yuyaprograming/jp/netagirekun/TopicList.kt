package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class TopicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_list)

        val z = intent!!.getStringArrayListExtra("topic_list")
        Log.d("zeus", z.toString())
    }
}
