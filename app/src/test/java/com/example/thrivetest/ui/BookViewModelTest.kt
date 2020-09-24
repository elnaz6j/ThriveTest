package com.example.thrivetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thrivetest.api.BookNetworkResult
import com.example.thrivetest.data.Book
import com.example.thrivetest.data.BookRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class BookViewModelTest {

    @Mock
    private lateinit var repository: BookRepository

    private lateinit var viewModel: BookViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = BookViewModel(repository)
    }

    @Test
    fun `given repository returns book list , viewModel should return book data`() {
        val book1 = Book(id = 1, title = "BookTitle1", author = "author1")
        val book2 = Book(id = 2, title = "BookTitle2", author = "author2")
        val books = listOf(book1, book2)
        val result: LiveData<BookNetworkResult<List<Book>>> =
            MutableLiveData(BookNetworkResult.Success(books))
        `when`(repository.getBooks()).thenReturn(result)

        assertEquals(viewModel.getBooks(), result)
    }

    @Test
    fun `given repository returns error, viewModel should return error`() {
        val errorResult: LiveData<BookNetworkResult<List<Book>>> =
            MutableLiveData(BookNetworkResult.Error(Exception()))
        `when`(repository.getBooks()).thenReturn(errorResult)

        assertEquals(viewModel.getBooks(), errorResult)
    }

    @Test
    fun `given viewModel get cleared, should clear repository subscription`() {
        viewModel.onCleared()

        verify(repository).clearSubscription()
    }
}