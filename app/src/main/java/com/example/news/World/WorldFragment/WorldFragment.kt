package com.example.news.World.WorldFragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.World.WorldAdapter.HeadlineRecyclerViewAdapter
import com.example.news.World.WorldAdapter.TrendingNewsadapter
import com.example.news.World.WorldAdapter.WorldNewsFeedAdapter
import com.example.news.World.WorldModel.WorldHeadLineModel
import com.example.news.World.WorldModel.WorldNewsFeedModel
import com.example.news.World.WorldModel.WorldTrendingModel
import com.example.news.World.viewModel.WorldHeadLinesViewModel
import com.example.news.World.viewModel.WorldNewsViewModel
import com.example.news.World.viewModel.WorldTrendingNewsViewModel
import com.example.news.activity.HeadLinePageListner
import com.example.news.activity.PageListnerCallback
import kotlinx.coroutines.*


class WorldFragment : Fragment(){

    private var worldHeadlineList : ArrayList<WorldHeadLineModel>? = null
    private var recyclerView : RecyclerView?= null
    private var headlineRecyclerViewAdapter : HeadlineRecyclerViewAdapter? = null

    private var worldNewsFeedAdapter : WorldNewsFeedAdapter? = null
    private var worldNewsList : ArrayList<WorldNewsFeedModel>? = null
    private var newsRecyclerView : RecyclerView? = null

    private var trendingNewsList : ArrayList<WorldTrendingModel>? = null
    private var trendingNewsadapter : TrendingNewsadapter? = null

    private var trendnewrecycleviewer : RecyclerView? = null
    private var scope : CoroutineScope? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        worldHeadlineList = ArrayList<WorldHeadLineModel>()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_world,container,false)
        val worldNewsAsyncTask = WorldNewsAsyncTask(view?.context,view)
        worldNewsAsyncTask.execute()

        worldHeadLineNews(context,view)
        trendingWorldNews(context,view)
        worldOtherNews(context,view)

        return view
    }

    inner class WorldNewsAsyncTask(val context: Context?,val view: View) : AsyncTask<Void,Void,Void>(){

        override fun doInBackground(vararg params: Void?): Void? {

            return null
        }
    }

    private fun worldOtherNews(context: Context?, view: View?) {

        newsRecyclerView = view?.findViewById(R.id.recyclerviewothernews)

        newsRecyclerView?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        worldNewsFeedAdapter = WorldNewsFeedAdapter()

        worldNewsFeedAdapter?.clearWorldNews()

        val noteViewModel =  ViewModelProviders.of(this).get(WorldNewsViewModel::class.java)
        noteViewModel.getAllWorldNews()

        noteViewModel.liveData?.observe(this,Observer<ArrayList<WorldNewsFeedModel>> { t -> worldNewsFeedAdapter?.setWorldNews(t) })

        newsRecyclerView?.adapter = worldNewsFeedAdapter
    }

    private fun worldHeadLineNews(context: Context?,view: View){

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView = view.findViewById(R.id.recyclerviewheadline)
        recyclerView?.layoutManager = layoutManager

        headlineRecyclerViewAdapter = HeadlineRecyclerViewAdapter()
        headlineRecyclerViewAdapter?.clearWorldHeadLineNews()

        val noteHeadLineViewModel = ViewModelProviders.of(this).get(WorldHeadLinesViewModel :: class.java)
        noteHeadLineViewModel.getAllWorldHeadLinesNews()

        noteHeadLineViewModel.liveData?.observe(this,Observer<ArrayList<WorldHeadLineModel>>{ t -> headlineRecyclerViewAdapter?.setWorldHeadLineNews(t) })

        recyclerView?.adapter = headlineRecyclerViewAdapter

    }
    private fun trendingWorldNews(context: Context?,view : View){

        trendnewrecycleviewer = view.findViewById(R.id.trendingWorldNews)
        trendnewrecycleviewer?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        trendingNewsadapter = TrendingNewsadapter()
        trendingNewsadapter?.setWorldNews(trendingNewsList)

        trendingNewsadapter?.clearWorldNews()
        val trendingNewsViewModel =  ViewModelProviders.of(this).get(WorldTrendingNewsViewModel::class.java)
        trendingNewsViewModel.getAllWorldNews()

        trendingNewsViewModel.liveData?.observe(this,Observer<ArrayList<WorldTrendingModel>>{ t -> trendingNewsadapter?.setWorldNews(t)})
        trendnewrecycleviewer?.adapter = trendingNewsadapter

    }
    fun getPageListner(pageListnerCallback : PageListnerCallback){

        val pageListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                Log.e("check position","$position")
                trendingNewsadapter?.setSelectedIndex(position)
//                indicatorAdapter.notifyDataSetChanged()
                trendnewrecycleviewer?.scrollToPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        }
        pageListnerCallback.getPageListnerCallback(pageListner = pageListener)
    }
    fun getHeadLinePageListnerCallback(headLinePageListner: HeadLinePageListner){

        val pageListener = object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) {

                headlineRecyclerViewAdapter?.setSelectedIndex(position)
                recyclerView?.scrollToPosition(position)
            }

            override fun onPageSelected(position: Int) {
            }
        }
        headLinePageListner.getHeadLinePageListnerCallback(pageListener = pageListener)
    }
}
