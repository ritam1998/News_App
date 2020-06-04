package com.example.news.ViewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var allItemLists = ArrayList<Fragment>()
    private var titleList = ArrayList<String>()

    override fun getCount(): Int {
        return allItemLists.size
    }

    override fun getItem(position: Int): Fragment {
        return allItemLists[position]
    }

    fun addFragment(fragment : Fragment, title : String){
        allItemLists.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}