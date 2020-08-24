package yuyaprograming.jp.netagirekun

import java.io.Serializable
import java.util.Date
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Task : RealmObject(), Serializable {
    var title: String = ""      // タイトル
    var contents: String = ""   // 内容

    // id をプライマリーキーとして設定
    @PrimaryKey
    var id: Int = 0
}