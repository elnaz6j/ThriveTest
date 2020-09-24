package com.example.thrivetest.ui

import androidx.lifecycle.ViewModel
import com.example.thrivetest.data.BookRepository

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun getBooks() = bookRepository.getBooks()

    public override fun onCleared() {
        super.onCleared()
        bookRepository.clearSubscription()
    }
}