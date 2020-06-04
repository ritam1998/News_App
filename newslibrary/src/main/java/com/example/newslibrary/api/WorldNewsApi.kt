package com.example.newslibrary.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WorldNewsApi {

    // Fetch All Worlds News
    @GET("everything")
    fun getAllPosts(
        @Query("domains") domains : String,
        @Query("apiKey") apiKey: String?
    ) : Call<JsonObject>

    // Fetch ALL WorldHeadLines

    @GET("top-headlines")
    fun worldHeadLine(
        @Query("sources") sources : String,
        @Query("apiKey") apiKey : String
    ) : Call<JsonObject>
}