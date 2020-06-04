package com.example.news.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.World.WorldFragment.TrendingNewsFragment
import com.example.news.World.WorldFragment.WorldFragment
import com.example.news.World.WorldModel.WorldTrendingModel


class TrendingNewsActivity : AppCompatActivity(),PageListnerCallback {

    private var listoftrendingNews : ArrayList<WorldTrendingModel>? = null
    private var trendingNewsViewpager : ViewPager? = null
    private var position : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_news)

        trendingNewsViewpager = findViewById(R.id.viewTrendingNews)

        position = intent?.extras?.getInt("position")
        Log.e("position","$position")

        listoftrendingNews = intent?.getParcelableArrayListExtra<WorldTrendingModel>("listoftrendingNews")
        Log.e("list","$listoftrendingNews")

        val trendingNewsBriefAdapter = TrendingNewsBriefAdapter(supportFragmentManager,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        trendingNewsViewpager?.adapter = trendingNewsBriefAdapter

        trendingNewsViewpager?.currentItem = position ?: 0

        WorldFragment().getPageListner(this)

        val window = window
        window?.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun getPageListnerCallback(pageListner: ViewPager.OnPageChangeListener) {
        trendingNewsViewpager?.addOnPageChangeListener(pageListner)
    }

    inner class TrendingNewsBriefAdapter(fm : FragmentManager,behaviour : Int) : FragmentStatePagerAdapter(fm,behaviour) {

        override fun getItem(position: Int): Fragment {

            val trendingNewsFragment = TrendingNewsFragment()
            trendingNewsFragment.trendingNewsList(listoftrendingNews?.get(position))

            return trendingNewsFragment
        }

        override fun getCount(): Int {
            return listoftrendingNews?.size ?: 0
        }
    }
}
interface PageListnerCallback{
    fun getPageListnerCallback(pageListner: ViewPager.OnPageChangeListener)
}
