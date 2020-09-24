package com.example.thrivetest.api

sealed class BookNetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : BookNetworkResult<T>()
    data class Error(val exception: Throwable) : BookNetworkResult<Nothing>()
}