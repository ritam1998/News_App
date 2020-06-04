package com.example.news.World.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.news.World.WorldModel.WorldHeadLineModel
import com.example.newslibrary.worldNews.GetAllHeadlinesNews
import com.example.newslibrary.worldNews.WorldHeadLinescallingApi
import org.json.JSONObject

class WorldHeadLinesViewModel(application: Application) : AndroidViewModel(application),GetAllHeadlinesNews {

    val allHeadLineList = ArrayList<WorldHeadLineModel>()
    val context = getApplication<Application>().applicationContext
    private var worldHeadLinescallingApi : WorldHeadLinescallingApi? = null

    var liveData : MutableLiveData<ArrayList<WorldHeadLineModel>>? = null

    lateinit var worldHeadLineModel: WorldHeadLineModel
    init {
        if (liveData == null) {
            liveData = MutableLiveData()
        }
    }

    override fun getHeadLineNews(worldHeadLineNews: String) {

        val worldNewsJsonObject = JSONObject(worldHeadLineNews?:"")
        val newsContentjsonArray = worldNewsJsonObject.getJSONArray("articles")

        for(i in 0 until newsContentjsonArray.length()){

            val getJsonNewsdata = newsContentjsonArray.getJSONObject(i)
            val nameOfJournal = newsContentjsonArray.getJSONObject(i).getJSONObject("source")


            worldHeadLineModel = WorldHeadLineModel(
                worldHeadlineTitle = getJsonNewsdata.getString("title"),
                worldHeadLinePublishTime = getJsonNewsdata.getString("publishedAt"),
                headlineImage = getJsonNewsdata.getString("urlToImage"),
                headLineNewsJournal = nameOfJournal.getString("name"),
                headLineNewsContent = getJsonNewsdata.getString("content"),
                headLineAuthor = getJsonNewsdata.getString("author"),
                headLineNewsUrl = getJsonNewsdata.getString("url"))

            allHeadLineList?.add(worldHeadLineModel)
        }

        newsLiveData(allHeadLineList)
    }

    fun getAllWorldHeadLinesNews(){

        worldHeadLinescallingApi = WorldHeadLinescallingApi()
        worldHeadLinescallingApi?.getWorldNewsHeadLines(this)
    }
    fun newsLiveData(result : ArrayList<WorldHeadLineModel>){
        liveData?.postValue(result)
    }
}