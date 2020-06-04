package com.example.news.World.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.news.World.WorldModel.WorldTrendingModel
import com.example.newslibrary.worldNews.GetALlTrendingNewsPost
import com.example.newslibrary.worldNews.TrendingNewsCallingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class WorldTrendingNewsViewModel(application: Application) : AndroidViewModel(application),
    GetALlTrendingNewsPost {

    val allUrlList = ArrayList<WorldTrendingModel>()
    val context = getApplication<Application>().applicationContext
    private var trendingNewsCallingApi : TrendingNewsCallingApi? = null

    var liveData : MutableLiveData<ArrayList<WorldTrendingModel>>? = null

    lateinit var worldTrendingModel: WorldTrendingModel
    init {
        if (liveData == null) {
            liveData = MutableLiveData()
        }
    }
    override fun worldtrendingnewspost(worldTrendingNews: String?) {

        Log.e("news","$worldTrendingNews")

        val worldNewsJsonObject = JSONObject(worldTrendingNews?:"")
        val newsContentjsonArray = worldNewsJsonObject.getJSONArray("articles")

        for(i in 0 until newsContentjsonArray.length()){

            val getJsonNewsdata = newsContentjsonArray.getJSONObject(i)
            val nameOfJournal = newsContentjsonArray.getJSONObject(i).getJSONObject("source")

            Log.e("delete json","${getJsonNewsdata.getString("content").equals("null")}")

            worldTrendingModel = WorldTrendingModel(trendingNewsJournal = nameOfJournal.getString("name"),
                trendingNewsTitle = getJsonNewsdata.getString("title"),
                trendingNewsAuthor = getJsonNewsdata.getString("author"),
                trendingNewsContent = getJsonNewsdata.getString("content"),
                trendingNewsUrl = getJsonNewsdata.getString("url"),
                trendingImageUrl = getJsonNewsdata.getString("urlToImage"),
                trendingNewsTime = getJsonNewsdata.getString("publishedAt"),
                newsTypeImage = 0)

            allUrlList.add(worldTrendingModel)
        }
        newsLiveData(allUrlList)
    }
    fun getAllWorldNews(){
        trendingNewsCallingApi = TrendingNewsCallingApi()
        trendingNewsCallingApi?.getWorldTrendingNewsPosts(this)
    }
    fun newsLiveData(result : ArrayList<WorldTrendingModel>){
        liveData?.postValue(result)
    }
}