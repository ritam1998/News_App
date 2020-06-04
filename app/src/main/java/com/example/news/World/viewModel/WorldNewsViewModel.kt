package com.example.news.World.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.news.World.WorldModel.WorldNewsFeedModel
import com.example.newslibrary.worldNews.GetALlNewsPost
import com.example.newslibrary.worldNews.WorldNewsCallingApi
import org.json.JSONObject


class WorldNewsViewModel(application: Application) : AndroidViewModel(application),GetALlNewsPost {

    val allUrlList = ArrayList<WorldNewsFeedModel>()
    val context = getApplication<Application>().applicationContext
    private var worldNewsCallingApi : WorldNewsCallingApi? = null

    var liveData : MutableLiveData<ArrayList<WorldNewsFeedModel>>? = null

    lateinit var allNewsFeedData : WorldNewsFeedModel
    init {
        if (liveData == null) {
            liveData = MutableLiveData()
        }
    }
    override fun worldnewspost(worldNews: String?) {

        val worldNewsJsonObject = JSONObject(worldNews?:"")
        val newsContentjsonArray = worldNewsJsonObject.getJSONArray("articles")

        for(i in 0 until newsContentjsonArray.length()){

            val getJsonNewsdata = newsContentjsonArray.getJSONObject(i)
            val nameOfJournal = newsContentjsonArray.getJSONObject(i).getJSONObject("source")

             allNewsFeedData = WorldNewsFeedModel(worldheadLine = getJsonNewsdata.getString("title"),
                worldNewsBody = getJsonNewsdata.getString("description"),worldNewsPostDate = getJsonNewsdata.getString("publishedAt"),
                worldImageView = getJsonNewsdata.getString("urlToImage"),nameOfJournal = nameOfJournal.getString("name"),
                 newsContent = getJsonNewsdata.getString("content"),authorName = getJsonNewsdata.getString("author"),
                 fullContentRead = getJsonNewsdata.getString("url"),isRead = false)

            allUrlList.add(allNewsFeedData)
        }
        newsLiveData(allUrlList)
    }
    fun getAllWorldNews(){

        worldNewsCallingApi = WorldNewsCallingApi()
        worldNewsCallingApi?.getWorldNewsPosts(this)
    }
    fun newsLiveData(result : ArrayList<WorldNewsFeedModel>){
        liveData?.postValue(result)
    }
}