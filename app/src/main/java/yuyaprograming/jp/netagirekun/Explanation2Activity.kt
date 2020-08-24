package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_explanation2.*

class Explanation2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation2)

        explanation2_button_left.setOnClickListener {
            val intent = Intent(this, Explanation1Activity::class.java)
            startActivity(intent)
        }

        explanation2_button_right.setOnClickListener {
            val intent = Intent(this, Explanation3Activity::class.java)
            startActivity(intent)
        }
    }
}
