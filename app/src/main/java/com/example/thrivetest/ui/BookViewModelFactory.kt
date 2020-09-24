package com.example.thrivetest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thrivetest.data.BookRepository

@Suppress("UNCHECKED_CAST")
class BookViewModelFactory(private val bookRepository: BookRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookViewModel(bookRepository) as T
    }
}