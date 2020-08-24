package yuyaprograming.jp.netagirekun

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TopicAdapter(context: Context): BaseAdapter() {
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
    var topicList = mutableListOf<Topic>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return topicList.size
    }

    override fun getItem(position: Int): Any {
        return topicList[position]
    }

    override fun getItemId(position: Int): Long {
        return topicList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)

        textView1.text = topicList[position].contents

        return view
    }
}