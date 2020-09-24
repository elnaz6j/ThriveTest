package com.example.thrivetest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thrivetest.api.BookNetworkResult
import com.example.thrivetest.api.BookNetworkService
import com.example.thrivetest.util.DependencyInjectorUtil
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class BookRepositoryTest {

    @get:Rule
    val repositoryRule = InstantTaskExecutorRule()
    private lateinit var repository: BookRepository

    @Mock
    private lateinit var bookNetworkService: BookNetworkService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        repository = DependencyInjectorUtil.provideBookRepository(bookNetworkService)
    }

    @After
    fun tearDown() {
        repository.resetInstance()
    }

    @Test
    fun `given successful api data response, should return books`() {
        val book1 = Book(id = 1, title = "BookTitle1", author = "author1")
        val book2 = Book(id = 2, title = "BookTitle2", author = "author2")
        val book3 = Book(id = 3, title = "BookTitle3", author = "author3")
        val books = listOf(book1, book2, book3)
        `when`(bookNetworkService.getBooks()).thenReturn(Single.just(books))

        val result = repository.getBooks().value
        assertEquals(result, BookNetworkResult.Success(books))
    }

    @Test
    fun `given failure api data response, should return error`() {
        `when`(bookNetworkService.getBooks()).thenReturn(Single.error(Exception("error!")))

        val result = repository.getBooks().value
        assertTrue(result is BookNetworkResult.Error)
    }
}