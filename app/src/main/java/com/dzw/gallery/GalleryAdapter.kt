package com.dzw.gallery

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zhangwei on 2020/5/28.
 * 展示假数据
 */
class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ItemHolder>() {
    val size = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.title.text = "再等等具体等什么我也不知道，就是想再等等${position % size}"
    }


    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_title)
    }
}