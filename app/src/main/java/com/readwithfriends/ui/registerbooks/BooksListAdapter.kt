package com.readwithfriends.ui.registerbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.readwithfriends.extensions.listen
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.dto.BookDto
import kotlinx.android.synthetic.main.activity_detailbook.view.*
import kotlinx.android.synthetic.main.layout_book_list_item.view.*
import kotlinx.android.synthetic.main.layout_book_list_item.view.bookAuthor
import kotlinx.android.synthetic.main.layout_book_list_item.view.bookTitle

class BooksListAdapter(private val items: MutableList<BookDto?>, private var onClick : (BookDto?)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenericViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_book_list_item, parent, false)).listen{ pos, type ->
            val item = items.get(pos)
            onClick(item)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.bookAuthor.text = item?.authors
        holder.itemView.bookTitle.text = item?.title
        holder.itemView.bookListAdaperIsbn.text = item?.isbn
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class GenericViewHolder(view: View): RecyclerView.ViewHolder(view)