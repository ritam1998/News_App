package com.example.newslibrary.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WorldNewsTrendingApi {

    // Fetch All Top Headlines
    @GET("top-headlines")
    fun getAllTrendingNews(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : Call<JsonObject>
}