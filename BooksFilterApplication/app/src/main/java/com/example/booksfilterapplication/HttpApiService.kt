package com.example.booksfilterapplication

import retrofit2.http.GET

interface HttpApiService {
    @GET("/books")
    suspend fun getBooksData():List<BookData>
}