package com.example.news.World.WorldAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.World.WorldModel.WorldNewsFeedModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.cardview.widget.CardView
import com.example.news.activity.NewsBriefActivity


class WorldNewsFeedAdapter :
    RecyclerView.Adapter<WorldNewsFeedAdapter.WorldNewsFeedViewHolder>() {

    private var newsList : ArrayList<WorldNewsFeedModel>? = null
    private var currentPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldNewsFeedAdapter.WorldNewsFeedViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.world_news_show_new,parent,false)

        val worldNewsFeedViewHolder = WorldNewsFeedViewHolder(view)

        worldNewsFeedViewHolder.selectedCardNews.setOnClickListener {

            newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.isRead = true
            setData(newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.isRead,worldNewsFeedViewHolder)

            val intent = Intent(parent.context,NewsBriefActivity :: class.java)
            intent.apply {
                putExtra("newsOfJournal",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.nameOfJournal)
                putExtra("worldNewsImage",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.worldImageView)
                putExtra("worldnewsTitle",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.worldheadLine)
                putExtra("authorname",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.authorName)
                putExtra("postedDate",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.worldNewsPostDate)
                putExtra("content",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.newsContent)
                putExtra("fullnewsurl",newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.fullContentRead)
            }
            parent.context.startActivity(intent)
        }
        return worldNewsFeedViewHolder
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: WorldNewsFeedAdapter.WorldNewsFeedViewHolder, position: Int) {
        newsList?.get(position)?.let { holder.bindViewHolder(it) }
    }
    fun setData(checkRead: Boolean?,worldNewsFeedViewHolder: WorldNewsFeedViewHolder){
        Log.e("checkRead","$checkRead")

        val worldNewsHeadLine = newsList?.get(worldNewsFeedViewHolder.adapterPosition)?.worldheadLine
        worldNewsFeedViewHolder.textViewHeadlineNews.text = worldNewsHeadLine

        if(checkRead ==  true){
            worldNewsFeedViewHolder.textViewHeadlineNews.setTextColor(Color.GRAY)
        }
    }
    fun clearWorldNews(){
        newsList?.clear()
    }
    fun setWorldNews(notes: ArrayList<WorldNewsFeedModel>?){
        this.newsList = notes
        notifyDataSetChanged()
    }

    class WorldNewsFeedViewHolder(var itview : View) : RecyclerView.ViewHolder(itview) {

        var newImageView  = itview.findViewById(R.id.imageViewinnewsfeed) as ImageView
        var textViewHeadlineNews = itview.findViewById(R.id.textViewHeadline) as TextView
        var newsInBrief = itview.findViewById(R.id.newsInBrief) as TextView
        var postedTime = itview.findViewById(R.id.postdate) as TextView

        var selectedCardNews =  itview.findViewById(R.id.worldnewscardview) as CardView

        private val REMOVE_TAGS = Pattern.compile("<.+?>")

        fun bindViewHolder(worldNewsFeedModel: WorldNewsFeedModel){

            Picasso.get().load(worldNewsFeedModel.worldImageView).into(newImageView)

            val removeHtmlTag = removeTags(worldNewsFeedModel.worldNewsBody)

            textViewHeadlineNews.text = worldNewsFeedModel?.worldheadLine
            newsInBrief.setText(removeHtmlTag)

            val postedTimeChangeFormat = worldNewsFeedModel?.worldNewsPostDate

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
            val date = inputFormat.parse(postedTimeChangeFormat)
            val formattedDate = outputFormat.format(date)

            postedTime.text = "Date : "+formattedDate
        }

        fun removeTags(removeHtmlTagInNews : String?): String? {
            if (removeHtmlTagInNews == null || removeHtmlTagInNews.length === 0) {
                return removeHtmlTagInNews
            }
            val m = REMOVE_TAGS.matcher(removeHtmlTagInNews)
            return m.replaceAll("")
        }
    }
}