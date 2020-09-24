package com.example.thrivetest.data

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thrivetest.api.BookNetworkResult
import com.example.thrivetest.api.BookNetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookRepository private constructor(private val bookNetworkService: BookNetworkService) {

    private val subscription = CompositeDisposable()

    fun getBooks(): LiveData<BookNetworkResult<List<Book>>> {
        val liveBooks = MutableLiveData<BookNetworkResult<List<Book>>>()
        subscription.add(
            bookNetworkService.getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ books ->
                    liveBooks.value = BookNetworkResult.Success(books)
                }, { error ->
                    liveBooks.value = BookNetworkResult.Error(error)
                })
        )
        return liveBooks
    }

    fun clearSubscription() = subscription.clear()

    @VisibleForTesting
    fun resetInstance() {
        instance = null
    }

    companion object {

        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(bookNetworkService: BookNetworkService) = instance ?: synchronized(this) {
            instance ?: BookRepository(bookNetworkService).also { instance = it }
        }
    }
}