package com.example.thrivetest.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivetest.data.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setUp(book: Book) {
        itemView.book_title.text = book.title
        itemView.book_author.text = book.author
    }
}