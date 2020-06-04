package com.example.news.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.news.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_brief_news.*
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class NewsBriefActivity : AppCompatActivity(), View.OnClickListener {

    private var imageView : ImageView? = null
    private var nameOfJournal : Toolbar? = null
    private var worldNewsCollapsingToolbar : CollapsingToolbarLayout?= null

    private var worldNewsTitle : TextView?= null

    private val REMOVE_NUMBER = Pattern.compile("[0-9]")
    private var readMoreText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brief_news)

        imageView = findViewById(R.id.briefnewsImageView)
        nameOfJournal = findViewById(R.id.toolbar_title)
        worldNewsCollapsingToolbar = findViewById(R.id.worldnewscollapsingtoolbar)

        worldNewsTitle = findViewById(R.id.worldnewsTitle)
        readMoreText = findViewById(R.id.readmore)

        setSupportActionBar(nameOfJournal)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val window = window
        window?.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.statusBarColor = Color.TRANSPARENT
        }
        worldNewsfeed()
    }
    private fun worldNewsfeed(){

        val worldNewsJournal = intent?.extras?.getString("newsOfJournal")
        val worldNewsImageView = intent?.extras?.getString("worldNewsImage")

        Picasso.get().load(worldNewsImageView).into(imageView)
//        nameOfJournal?.title = worldNewsJournal

        worldNewsCollapsingToolbar?.title = worldNewsJournal

        worldNewsTitle?.text = intent?.extras?.getString("worldnewsTitle")
        authorName?.text = "By - "+newsContentNullableCheck(intent?.extras?.getString("authorname"))

        val formatDateTime = setTime(intent?.extras?.getString("postedDate"))
        posteddateTime?.text = formatDateTime

        val newsContentNumberRemove = newsContentNullableCheck(intent?.extras?.getString("content"))
        newscontent?.text =  removeTagsInString(newsContentNumberRemove)

        readMoreText?.setOnClickListener(this)
    }
    fun setTime(postedTimeChangeFormat : String?): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
        val date = inputFormat.parse(postedTimeChangeFormat)
        val formattedDate = outputFormat.format(date)

        return formattedDate
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        if(item.itemId == R.id.share){

            val worldNewsContent = intent?.extras?.getString("fullnewsurl")
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.apply {
                type = "text/plain"
                putExtra(android.content.Intent.EXTRA_TEXT,worldNewsContent)
            }
            startActivity(Intent.createChooser(intent, "Share With"))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun removeTagsInString(removeAllTag : String?): String? {

        var removeString : String? = null
        if(removeAllTag == null || removeAllTag.length > 0){
            removeString = removeAllTag?.replace("[","")?.replace("]","")
                ?.replace("chars","")?.replace("+","")
        }
        return removeString?.substring(0,removeString?.length - 3)
    }
    override fun onClick(v: View?) {

        val worldNewsFullContent = intent?.extras?.getString("fullnewsurl")
        val bundle = Bundle()
        bundle.putString("worldNewsFullContent",worldNewsFullContent)

        val  intent =  Intent(this,WebViewActivity :: class.java)
        intent.apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
    fun newsContentNullableCheck(content : String?): String? {

        if(content == "null"){
            return "unknown    "
        }
        return content
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.share_menu,menu)
        return true
    }

}