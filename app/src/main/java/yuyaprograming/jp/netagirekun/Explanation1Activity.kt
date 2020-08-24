package yuyaprograming.jp.netagirekun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_explanation1.*

class Explanation1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation1)

        explanation1_button_left.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        explanation1_button_right.setOnClickListener {
            val intent = Intent(this, Explanation2Activity::class.java)
            startActivity(intent)
        }
    }
}
