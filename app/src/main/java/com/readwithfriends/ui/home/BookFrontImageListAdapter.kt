package com.readwithfriends.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_book_image_list_item.view.*


class BookFrontImageListAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return GenericViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_image_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = imageUrls[position]
            Picasso.get().load(item).into(holder.itemView.bookFrontImage);
        }

        override fun getItemCount(): Int {
            return imageUrls.size
    }
}

//class BookFrontViewHolder(view: View): RecyclerView.ViewHolder(view)
