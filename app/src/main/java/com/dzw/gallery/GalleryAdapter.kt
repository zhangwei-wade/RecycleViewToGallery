package com.dzw.gallery

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zhangwei on 2020/5/28.
 */
class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

    }


    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}