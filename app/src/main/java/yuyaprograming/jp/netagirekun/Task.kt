package yuyaprograming.jp.netagirekun

import java.io.Serializable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Task : RealmObject(), Serializable {
    var title: String = ""      // ジャンル

    // id をプライマリーキーとして設定
    @PrimaryKey
    var id: Int = 0
}