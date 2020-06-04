package com.example.newslibrary.worldNews

import android.util.Log
import com.example.newslibrary.api.WorldNewsApi
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorldHeadLinescallingApi {

    val BASE_URL = "http://newsapi.org/v2/"
    private val retrofit = Retrofit.Builder()

    fun getWorldNewsHeadLines(getALlNewsHeadLinePost: GetAllHeadlinesNews) {

        retrofit.apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }

        val postHeadLinesApi = retrofit.build().create(WorldNewsApi::class.java)

        val callAllNewsHeadLines = postHeadLinesApi.worldHeadLine("techcrunch", "a8a3e9daff5c4c08b605c76c7182cf1c")

        callAllNewsHeadLines.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Failed","${t.message}")
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if (!response.isSuccessful) {
                    Log.e("EROR", " ${response.code()}")
                } else {

                    val getAllPostData = response.body().toString()
                    Log.e("All Data", "${getAllPostData}")

                    getALlNewsHeadLinePost?.getHeadLineNews(worldHeadLineNews = getAllPostData)
                }
            }
        })
    }
}
interface GetAllHeadlinesNews{
    fun getHeadLineNews(worldHeadLineNews : String)
}