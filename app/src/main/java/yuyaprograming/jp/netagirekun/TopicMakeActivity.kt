package yuyaprograming.jp.netagirekun

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort
import kotlinx.android.synthetic.main.topic_detail_input.*
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

    private var mTask: Task? = null

    private val mOnDoneClickListener = View.OnClickListener {
        addTask()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_make)

        topic_make_button.setOnClickListener {
            val intent = Intent(this, TopicDetailActivity::class.java)
            startActivity(intent)
        }

        topic_make_input_done_button.setOnClickListener(mOnDoneClickListener)

        // EXTRA_TASK から Task の id を取得して、 id から Task のインスタンスを取得する
        val intent = intent
        val taskId = intent.getIntExtra(EXTRA_TASK, -1)
        val realm = Realm.getDefaultInstance()
        mTask = realm.where(Task::class.java).equalTo("id", taskId).findFirst()
        realm.close()

        if (mTask == null) {
            // 新規作成の場合
        } else {
            // 更新の場合
            title_edit_text.setText(mTask!!.title)
            content_edit_text.setText(mTask!!.contents)
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

    private fun addTask() {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()

        if (mTask == null) {
            // 新規作成の場合
            mTask = Task()

            val taskRealmResults = realm.where(Task::class.java).findAll()

            val identifier: Int =
                if (taskRealmResults.max("id") != null) {
                    taskRealmResults.max("id")!!.toInt() + 1
                } else {
                    0
                }
            mTask!!.id = identifier
        }

        val title = title_edit_text.text.toString()
        val content = content_edit_text.text.toString()

        mTask!!.title = title
        mTask!!.contents = content

        realm.copyToRealmOrUpdate(mTask!!)
        realm.commitTransaction()

        realm.close()
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
