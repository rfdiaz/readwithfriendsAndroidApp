package com.readwithfriends.ui.registerbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.readwithfriends.model.api.model.BookBackendResponse
import kotlinx.android.synthetic.main.layout_book_list_item.view.*

class BooksListAdapter(private val items: List<BookBackendResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenericViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.bookAuthor.text = item.authors
        holder.itemView.bookTitle.text = item.title
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class GenericViewHolder(view: View): RecyclerView.ViewHolder(view)