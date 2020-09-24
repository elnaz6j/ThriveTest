package com.example.thrivetest.util

import com.example.thrivetest.api.BookNetworkService
import com.example.thrivetest.data.BookRepository

/**
 * [DependencyInjectorUtil] implements simple manual dependency injection
 * The idea is to use one place for all the dependencies that app needs to help
 * app testability
 */
object DependencyInjectorUtil {

    fun provideBookNetworkService() = BookNetworkService.getInstance()

    fun provideBookRepository(networkService: BookNetworkService) =
        BookRepository.getInstance(networkService)
}