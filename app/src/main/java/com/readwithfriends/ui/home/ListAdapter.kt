package com.readwithfriends.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class ListAdapter(private val items: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenericViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.username.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class GenericViewHolder(view: View): RecyclerView.ViewHolder(view)