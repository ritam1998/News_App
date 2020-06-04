package com.example.news.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.World.WorldFragment.HeadLineNewsFragment
import com.example.news.World.WorldFragment.WorldFragment
import com.example.news.World.WorldModel.WorldHeadLineModel
import com.google.android.material.appbar.AppBarLayout

class WorldHeadLineActivity : AppCompatActivity(), HeadLinePageListner {

    private var position : Int? = 0
    private var worldHeadLineNewsViewpager : ViewPager? = null
    private var listOfHeadLineNews :  ArrayList<WorldHeadLineModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_head_line)

        worldHeadLineNewsViewpager = findViewById(R.id.viewHeadlineNews)

        position = intent?.extras?.getInt("position")
        listOfHeadLineNews = intent?.getParcelableArrayListExtra<WorldHeadLineModel>("listOfHeadLines")

        val headLineNewsBriefAdapter = HeadLieNewsBriefAdapter(supportFragmentManager,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        worldHeadLineNewsViewpager?.adapter = headLineNewsBriefAdapter

        worldHeadLineNewsViewpager?.currentItem = position ?: 0

        WorldFragment().getHeadLinePageListnerCallback(this)

        val window = window
        window?.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.statusBarColor = Color.TRANSPARENT
        }
    }
    override fun getHeadLinePageListnerCallback(pageListener: ViewPager.OnPageChangeListener) {

        worldHeadLineNewsViewpager?.addOnPageChangeListener(pageListener)
    }
    inner class HeadLieNewsBriefAdapter(fm : FragmentManager,behavior: Int) : FragmentStatePagerAdapter(fm,behavior){

        override fun getItem(position: Int): Fragment {

            val headLineNewsFragment = HeadLineNewsFragment()
            headLineNewsFragment?.headLineNewsList(listOfHeadLineNews?.get(position))
            return headLineNewsFragment
        }

        override fun getCount(): Int {
            return listOfHeadLineNews?.size ?: 0
        }
    }
}
interface HeadLinePageListner{
    fun getHeadLinePageListnerCallback(pageListener: ViewPager.OnPageChangeListener)
}
