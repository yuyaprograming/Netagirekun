package yuyaprograming.jp.netagirekun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort
import kotlinx.android.synthetic.main.topic_make_input.*
import java.util.*

const val EXTRA_TOPIC = "yuyaprograming.jp.netagirekun.TOPIC"

class TopicMakeActivity : AppCompatActivity() {
    private lateinit var nRealm: Realm
    private val nRealmListener = object : RealmChangeListener<Realm> {
        override fun onChange(element: Realm) {
            reloadListView()
        }
    }

    private lateinit var mTopicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_make)

        topic_make_button.setOnClickListener {
            val intent = Intent(this, TopicDetailActivity::class.java)
            startActivity(intent)
        }

        // Realmの設定
        nRealm = Realm.getDefaultInstance()
        nRealm.addChangeListener(nRealmListener)

        // ListViewの設定
        mTopicAdapter = TopicAdapter(this@TopicMakeActivity)

        // ListViewをタップしたときの処理
        listView2.setOnItemClickListener { parent, view, position, id ->
            // 入力・編集する画面に遷移させる
            val topic = parent.adapter.getItem(position) as Topic
            val intent = Intent(this@TopicMakeActivity, TopicDetailActivity::class.java)
            intent.putExtra(EXTRA_TOPIC, topic.id)
            startActivity(intent)
        }

        // ListViewを長押ししたときの処理
        listView2.setOnItemLongClickListener { parent, _, position, _ ->
            // タスクを削除する
            val topic = parent.adapter.getItem(position) as Topic

            // ダイアログを表示する
            val builder = AlertDialog.Builder(this@TopicMakeActivity)

            builder.setTitle("削除")
            builder.setMessage(topic.title + "を削除しますか")

            builder.setPositiveButton("OK"){_, _ ->
                val results = nRealm.where(Topic::class.java).equalTo("id", topic.id).findAll()

                nRealm.beginTransaction()
                results.deleteAllFromRealm()
                nRealm.commitTransaction()

                reloadListView()
            }

            builder.setNegativeButton("CANCEL", null)

            val dialog = builder.create()
            dialog.show()

            true
        }

        reloadListView()
    }

    private fun reloadListView() {
        // Realmデータベースから、「全てのデータを取得して新しい日時順に並べた結果」を取得
        val taskRealmResults = nRealm.where(Topic::class.java).findAll().sort("contents", Sort.DESCENDING)

        // 上記の結果を、TaskList としてセットする
        mTopicAdapter.topicList = nRealm.copyFromRealm(taskRealmResults)

        // TaskのListView用のアダプタに渡す
        listView2.adapter = mTopicAdapter

        // 表示を更新するために、アダプターにデータが変更されたことを知らせる
        mTopicAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        nRealm.close()
    }
}
