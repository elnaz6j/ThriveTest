package com.example.thrivetest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thrivetest.R
import com.example.thrivetest.api.BookNetworkResult
import com.example.thrivetest.util.DependencyInjectorUtil
import kotlinx.android.synthetic.main.activity_book_library.*

class BookLibraryActivity : AppCompatActivity() {

    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_library)
        setupBooksRecyclerView()
        setupBookViewModel()
    }

    private fun setupBooksRecyclerView() {
        bookAdapter = BookAdapter()
        books_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }
    }

    private fun setupBookViewModel() {

        val networkService = DependencyInjectorUtil.provideBookNetworkService()
        val bookRepository = DependencyInjectorUtil.provideBookRepository(networkService)

        val bookViewModel = ViewModelProvider(
            this, BookViewModelFactory(bookRepository)
        ).get(BookViewModel::class.java)

        bookViewModel.getBooks().observe(this, Observer { response ->
            when (response) {
                is BookNetworkResult.Success -> {
                    val books = response.data
                    bookAdapter.setBooks(books)
                }
                is BookNetworkResult.Error -> {
                    Toast.makeText(this, response.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}