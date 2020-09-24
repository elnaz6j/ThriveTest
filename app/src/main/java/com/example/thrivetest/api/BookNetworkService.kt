package com.example.thrivetest.api

import com.example.thrivetest.data.Book
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BookNetworkService {

    @GET("books")
    fun getBooks(): Single<List<Book>>

    companion object {
        private const val BASE_URL = "https://ivy-ios-challenge.herokuapp.com/"

        @Volatile
        private var instance: BookNetworkService? = null

        fun getInstance(): BookNetworkService = instance
            ?: synchronized(this) {
            instance ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(BookNetworkService::class.java)
                .also { instance = it }
        }
    }
}
