package com.example.news

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.news.Business.BusinessFragment
import com.example.news.Politics.PoliticsFragment
import com.example.news.Science.ScienceFragment
import com.example.news.Tech.TechFragment
import com.example.news.ViewPagerAdapter.ViewPagerAdapter
import com.example.news.World.WorldFragment.WorldFragment
import com.example.news.connection.ConnectionChangeCallback
import com.example.news.connection.LoadingDiaLog
import com.example.news.connection.NetworkChangeReceiver
import com.example.news.connection.NetworkConnection
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_connectionlost.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ConnectionChangeCallback {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    private var toolBar : Toolbar? = null
    private var mSearchView : SearchView? = null
    private var titleBar : TextView? = null

    private var drawerLayout : DrawerLayout? = null
    private var navigationView : NavigationView? = null


    private var viewpagerAdapter : ViewPagerAdapter?= null
    private var viewPager : ViewPager?= null
    private var tabLayout : TabLayout?= null

    private var networkChangeReceiver : NetworkChangeReceiver? = null
    private var dialog : Dialog? = null
    private var handler : Handler? = null
    private var mRunnable : Runnable? = null

    private var loadingDiaLog : LoadingDiaLog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSearchView = findViewById(R.id.search_view)
        titleBar = findViewById(R.id.toolbar_title)
        toolBar = findViewById(R.id.toolbar2)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        /*For Search Item */

        mSearchView?.setOnCloseListener(SearchView.OnCloseListener {

            val param = mSearchView?.layoutParams as LinearLayout.LayoutParams
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                param.gravity = Gravity.START
            }
            //set layout params to cause layout update
            titleBar?.visibility = View.VISIBLE
            mSearchView?.setLayoutParams(param)
            false
        })
        mSearchView?.setOnSearchClickListener {

            val param = mSearchView?.getLayoutParams() as LinearLayout.LayoutParams
            param.gravity = Gravity.START

            //set layout params to cause layout update
            titleBar?.visibility = View.GONE
            mSearchView?.setLayoutParams(param)
        }

        /*For Drawer Menu*/

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        setSupportActionBar(toolBar)

        navigationView?.bringToFront()

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navigationView?.setNavigationItemSelectedListener(this)


        /*Calling viewPager Adapter*/

        viewpagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.viewPager1)
        tabLayout = findViewById(R.id.tab)

        networkChangeReceiver = NetworkChangeReceiver()
        networkChangeReceiver?.setConnectionChangeCallback(this)

        handler = Handler(Looper.getMainLooper())
        mRunnable?.run()

        dialog = Dialog(this)

        loadingDiaLog = LoadingDiaLog(this)
        loadingDiaLog?.loadingalertDialog()
        mRunnable = Runnable {
            allfragment()
            loadingDiaLog?.dismissDialog()
        }
        handler?.postDelayed(mRunnable,5000)
    }

    private fun allfragment(){

        viewpagerAdapter?.addFragment(WorldFragment(), "World")
        viewpagerAdapter?.addFragment(TechFragment(), "Tech")
        viewpagerAdapter?.addFragment(BusinessFragment(), "Business")
        viewpagerAdapter?.addFragment(PoliticsFragment(), "Politics")
        viewpagerAdapter?.addFragment(ScienceFragment(), "Science")

        viewPager?.adapter = viewpagerAdapter
        tabLayout?.setupWithViewPager(viewPager)
    }
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkChangeReceiver,intentFilter)
    }
    override fun onConnectionChange(isConnected: Boolean) {

        val contextView = findViewById(R.id.context_view) as View

        if(isConnected){

            val snackbar = Snackbar.make(contextView,"Connected",Snackbar.LENGTH_SHORT)
//            snackbar.show()
        }else{
            val snackbar = Snackbar.make(contextView,"oops! SomeThing Went Wrong",Snackbar.LENGTH_LONG)
            snackbar.show()

            dialog?.setContentView(R.layout.dialog_connectionlost)

            dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
            dialog?.setCanceledOnTouchOutside(false)

            dialog?.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog

            val reConnect = dialog?.findViewById<TextView>(R.id.try_again)

            reConnect?.setOnClickListener {
                Log.e("try Again","recreate()")
                refresh()
            }
            dialog?.show()
        }
    }
    private fun refresh() {

       val intent = getIntent();
       overridePendingTransition(0, 0);
       intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
       finish();
       overridePendingTransition(0, 0);
       startActivity(intent);
   }
    override fun onBackPressed() {

        if(drawerLayout?.isDrawerOpen(GravityCompat.START)?:true){
            drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
}
