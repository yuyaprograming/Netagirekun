package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_explanation3.*

class Explanation3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation3)

        explanation3_button_left.setOnClickListener {
            val intent = Intent(this, Explanation2Activity::class.java)
            startActivity(intent)
        }

        explanation3_button_right.setOnClickListener {
            val intent = Intent(this, Explanation4Activity::class.java)
            startActivity(intent)
        }
    }
}
