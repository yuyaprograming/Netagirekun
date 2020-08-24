package yuyaprograming.jp.netagirekun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_explanation4.*

class Explanation4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation4)

        explanation4_button_left.setOnClickListener {
            val intent = Intent(this, Explanation1Activity::class.java)
            startActivity(intent)
        }

        explanation4_button_right.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}