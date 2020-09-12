package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_topic_list.*

class TopicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_list)

        val z = intent!!.getStringArrayListExtra("topic_list")
        val a = z.get(0)
        val b = z.get(1)
        val c = z.get(2)
        val d = z.get(3)
        val e = z.get(4)

        Topic_selected1.setText(a)
        Topic_selected2.setText(b)
        Topic_selected3.setText(c)
        Topic_selected4.setText(d)
        Topic_selected5.setText(e)

        Log.d("zeus", z.toString())
    }


}
