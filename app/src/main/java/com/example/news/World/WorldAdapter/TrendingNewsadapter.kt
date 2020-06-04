package com.example.news.World.WorldAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.World.WorldModel.WorldTrendingModel
import com.example.news.activity.TrendingNewsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trending_world_news.view.*
import java.text.SimpleDateFormat

class TrendingNewsadapter : RecyclerView.Adapter<TrendingNewsadapter.WorldTrendingNewsViewHolder>(){

    private var trendingWorldNewsList : ArrayList<WorldTrendingModel>? = null
    private var selectedIndex : Int? = 0


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): TrendingNewsadapter.WorldTrendingNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_world_news,parent,false)

        val worldTrendingNewsViewHolder = WorldTrendingNewsViewHolder(view)

        allTrendingNews(worldTrendingNewsViewHolder,parent.context)
        return worldTrendingNewsViewHolder
    }

    private fun allTrendingNews(worldTrendingNewsViewHolder: WorldTrendingNewsViewHolder?,context: Context?) {

        worldTrendingNewsViewHolder?.trendingCardView?.setOnClickListener {

            val position =  worldTrendingNewsViewHolder.adapterPosition
            Log.e("layout position","${position}")

            val intent = Intent(context,TrendingNewsActivity :: class.java)
            intent.apply {
                putParcelableArrayListExtra("listoftrendingNews",trendingWorldNewsList)
                putExtra("position",position)
            }
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return trendingWorldNewsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrendingNewsadapter.WorldTrendingNewsViewHolder,position: Int) {
        trendingWorldNewsList?.get(position)?.let { holder.trendingBindViewHolder(it) }
        Log.e("breaking","${trendingWorldNewsList?.get(position)?.trendingNewsJournal}")
    }

    fun setSelectedIndex(position: Int){
        this.selectedIndex = position
    }

    fun clearWorldNews(){
        trendingWorldNewsList?.clear()
    }

    fun setWorldNews(trendingWorldNewsList : ArrayList<WorldTrendingModel>?){
        this.trendingWorldNewsList = trendingWorldNewsList
        notifyDataSetChanged()
    }

    class WorldTrendingNewsViewHolder(var itview : View) : RecyclerView.ViewHolder(itview) {

        var imageView = itview.findViewById(R.id.myImageView1) as ImageView
        var titleTrendingNews =  itview.findViewById(R.id.myImageViewText1) as TextView
        var date = itview.findViewById(R.id.time) as TextView

        var trendingCardView = itview.findViewById(R.id.trendingCardView) as CardView

        fun trendingBindViewHolder(worldTrendingModel: WorldTrendingModel){

            if(worldTrendingModel.trendingNewsContent == "null"){
                itview.trendingCardView.visibility = View.GONE
            }else{
                Picasso.get().load(worldTrendingModel.trendingImageUrl).into(imageView)
                titleTrendingNews.text = worldTrendingModel.trendingNewsTitle

                val changeHour = timingShow(worldTrendingModel?.trendingNewsTime?:"")
                date.text = changeHour
            }
        }

        private fun timingShow(postedTimeChangeFormat : String): String {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputFormat = SimpleDateFormat("EEE, MMM d, yy")
            val date = inputFormat.parse(postedTimeChangeFormat)
            val formattedDate = outputFormat.format(date)

            return formattedDate
        }
    }
}