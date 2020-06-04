package com.example.news.World.WorldFragment

import android.content.Intent
import android.content.Intent.createChooser
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.World.WorldModel.WorldTrendingModel
import com.example.news.activity.WebViewActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_trending_news_brief.*
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class TrendingNewsFragment : Fragment(), View.OnClickListener {

    private var worldTrendingModel : WorldTrendingModel? = null
    private val REMOVE_TAGS = Pattern.compile("<.+?>")
    private var trendingNewsreadmore : TextView? = null

    private var shareButton : FloatingActionButton? = null

    fun trendingNewsList(get: WorldTrendingModel?) {
        this.worldTrendingModel = get
    }
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_trending_news_brief,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val worldnewscollapsingtoolbar = view.findViewById(R.id.worldnewscollapsingtoolbar) as CollapsingToolbarLayout
        worldnewscollapsingtoolbar.title = worldTrendingModel?.trendingNewsJournal

        val trendingbriefnewsImageView = view.findViewById(R.id.briefnewsImageView) as ImageView
        Picasso.get().load(worldTrendingModel?.trendingImageUrl).into(trendingbriefnewsImageView)

        val trendingworldnewsTitle = view.findViewById(R.id.worldnewsTitle) as TextView
        trendingworldnewsTitle?.text = worldTrendingModel?.trendingNewsTitle

        val trendingNewsauthorName = view.findViewById(R.id.authorName) as TextView
        trendingNewsauthorName.text = "By - "+newsContentNullableCheck(worldTrendingModel?.trendingNewsAuthor)

        val trendingNewsposteddateTime = view.findViewById(R.id.posteddateTime) as TextView
        trendingNewsposteddateTime.text = setTime(worldTrendingModel?.trendingNewsTime)

        val trendingNewsContent = view.findViewById(R.id.newscontent) as TextView
        val checkNull = newsContentNullableCheck(worldTrendingModel?.trendingNewsContent)
        val removeAllHtmltags = removeTags(checkNull)
        trendingNewsContent.text = removeTagsInString(removeAllHtmltags)

        trendingNewsreadmore = view.findViewById(R.id.readmore) as TextView
        trendingNewsreadmore?.setOnClickListener(this)

        shareButton = view.findViewById(R.id.share)
        shareButton?.setOnClickListener{

            val intent = Intent(Intent.ACTION_SEND)
            intent?.apply {
                setType("text/plain")
                putExtra(Intent.EXTRA_TEXT,worldTrendingModel?.trendingNewsUrl)
            }
            activity?.startActivity(createChooser(intent,"Share With"))
        }
    }
    fun setTime(postedTimeChangeFormat : String?): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
        val date = inputFormat.parse(postedTimeChangeFormat)
        val formattedDate = outputFormat.format(date)

        return formattedDate
    }
    fun removeTagsInString(removeAllTag : String?): String? {

        var removeString : String? = null
        if(removeAllTag == null || removeAllTag.length > 0){
            removeString = removeAllTag?.replace("[","")?.replace("]","")
                ?.replace("chars","")?.replace("+","")
        }
        return removeString?.substring(0,removeString?.length - 3)
    }
    fun removeTags(removeHtmlTagInNews : String?): String? {
        if (removeHtmlTagInNews == null || removeHtmlTagInNews.length === 0) {
            return removeHtmlTagInNews
        }
        val m = REMOVE_TAGS.matcher(removeHtmlTagInNews)
        return m.replaceAll("")
    }
    fun newsContentNullableCheck(content : String?): String? {
        if(content == "null"){
            return "unknown    "
        }
        return content
    }
    override fun onClick(v: View?) {

        val worldNewsFullContent = worldTrendingModel?.trendingNewsUrl
        val bundle = Bundle()
        bundle.putString("worldTrendingNewsFullContent",worldNewsFullContent)

        val  intent =  Intent(activity,WebViewActivity :: class.java)
        intent.apply {
            putExtras(bundle)
        }
        activity?.startActivity(intent)
    }
}