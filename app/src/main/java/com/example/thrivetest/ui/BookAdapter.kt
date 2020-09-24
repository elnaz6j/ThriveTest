package com.example.thrivetest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivetest.R
import com.example.thrivetest.data.Book

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private var books = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
    )

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.setUp(book)
    }

    fun setBooks(newBooks: List<Book>) {
        val diffResult = DiffUtil.calculateDiff(BookDiffCallback(newBooks, books))
        books = newBooks.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    private class BookDiffCallback(
        private val newBookList: List<Book>,
        private val oldBookList: List<Book>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldBookList.size
        override fun getNewListSize() = newBookList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldBookList[oldItemPosition].id == newBookList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldBookList[oldItemPosition] == newBookList[newItemPosition]
    }
}