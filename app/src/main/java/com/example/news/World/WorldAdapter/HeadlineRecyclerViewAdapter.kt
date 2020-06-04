package com.example.news.World.WorldAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.World.WorldModel.WorldHeadLineModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import android.net.ParseException
import androidx.cardview.widget.CardView
import com.example.news.activity.WorldHeadLineActivity
import kotlinx.android.synthetic.main.world_headline_news.view.*
import java.util.*
import kotlin.collections.ArrayList


class HeadlineRecyclerViewAdapter : RecyclerView.Adapter<HeadlineRecyclerViewAdapter.WorldHeadlineViewHolder>() {

    private var headlineList : ArrayList<WorldHeadLineModel>? = null
    private var selectedIndex : Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): WorldHeadlineViewHolder {

        val viewOfHeadline = LayoutInflater.from(parent.context).inflate(R.layout.world_headline_news,parent,false)

        val worldHeadlineViewHolder = WorldHeadlineViewHolder(viewOfHeadline)
        allHeadLineNewsClickable(worldHeadlineViewHolder,parent.context)
        return worldHeadlineViewHolder
    }

    private fun allHeadLineNewsClickable(worldHeadlineViewHolder: HeadlineRecyclerViewAdapter.WorldHeadlineViewHolder, context: Context?) {

        worldHeadlineViewHolder?.headLineCardView?.setOnClickListener {
            val position = worldHeadlineViewHolder?.adapterPosition

            val intent =  Intent(context,WorldHeadLineActivity :: class.java)
            intent?.apply {
                putParcelableArrayListExtra("listOfHeadLines",headlineList)
                putExtra("position",position)
            }
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return headlineList?.size ?: 0
    }

    override fun onBindViewHolder(holder: WorldHeadlineViewHolder, position: Int) {
        headlineList?.get(position)?.let { holder.bindViewHolder(it) }
    }

    fun clearWorldHeadLineNews(){
        headlineList?.clear()
    }

    fun setWorldHeadLineNews(headlineList : ArrayList<WorldHeadLineModel>?){
        this.headlineList = headlineList
        notifyDataSetChanged()
    }

    fun setSelectedIndex(position: Int) {
        this.selectedIndex = position
    }

    class WorldHeadlineViewHolder(val itview : View) : RecyclerView.ViewHolder(itview) {

        var worldHeadline = itview.findViewById(R.id.headline) as TextView
        var publishTime = itview.findViewById(R.id.publishtime) as TextView
        var imageView = itview.findViewById(R.id.headlineImage) as CircleImageView

        var headLineCardView = itview.findViewById(R.id.headline_card_view) as CardView

        fun bindViewHolder(worldHeadLineModel: WorldHeadLineModel){

            if(worldHeadLineModel?.headLineAuthor != "null" && worldHeadLineModel?.headLineNewsContent != "null"){

                worldHeadline.text = worldHeadLineModel.worldHeadlineTitle

                val changeDateformat = ConvertDateToReadableDate(worldHeadLineModel.worldHeadLinePublishTime)
                publishTime.text = changeDateformat
                Picasso.get().load(worldHeadLineModel.headlineImage).into(imageView)
            }else{
                headLineCardView.visibility = View.GONE
            }
        }
        fun ConvertDateToReadableDate(DateTime: String?): String? {
            if (DateTime != null) {
                if (DateTime != "") {
                    // the input should be in this format 2019-03-08 15:14:29
                    //if not you have to change the pattern in SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                    val newDate: Date
                    val currentDate = java.util.Date()
                    var hour = 0
                    var min = 0
                    var sec = 0
                    var dayName = ""
                    var dayNum = ""
                    var monthName = ""
                    var year = ""
                    var numOfMilliSecondPassed: Long = 0
                    val milliSecond = 86400000.0f // 1 day is 86400000 milliseconds
                    val numOfDayPass: Float

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

                    try {
                        newDate = dateFormat.parse(DateTime) // convert String to date
                        numOfMilliSecondPassed = currentDate.time - newDate.getTime() //get the difference in date in millisecond

                        hour = Integer.parseInt(android.text.format.DateFormat.format("hh", newDate) as String)
                        min = Integer.parseInt(
                            android.text.format.DateFormat.format(
                                "mm",
                                newDate
                            ) as String
                        )
                        sec = Integer.parseInt(
                            android.text.format.DateFormat.format(
                                "ss",
                                newDate
                            ) as String
                        )
                        dayName = android.text.format.DateFormat.format("EEEE", newDate) as String
                        dayNum = android.text.format.DateFormat.format("dd", newDate) as String
                        monthName = android.text.format.DateFormat.format("MMM", newDate) as String
                        year = android.text.format.DateFormat.format("yyyy", newDate) as String


                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    //Convert the milliseconds to days
                    numOfDayPass = numOfMilliSecondPassed / milliSecond


                    if (numOfDayPass < 1) {
//                        return "$hour:$min:$sec"
                        return "$hour"+" hours ago"
                    } else if (numOfDayPass >= 1 && numOfDayPass < 7) {
                        return "$dayName $hour:$min:$sec"
                    } else if (numOfDayPass >= 7 && numOfDayPass < 30) {
                        val weeks = numOfDayPass.toInt() / 7

                        return if (weeks > 1) {
                            "$weeks weeks ago"
                        } else {
                            "$weeks week ago"
                        }
                    } else if (numOfDayPass >= 30 && numOfDayPass < 90) {
                        val months = numOfDayPass.toInt() / 30

                        return if (months > 1) {
                            "$months months ago"
                        } else {
                            "$months month ago"
                        }
                    } else if (numOfDayPass >= 360 && numOfDayPass < 1080) {
                        val years = numOfDayPass.toInt() / 360

                        return if (years > 1) {
                            "$years years ago"
                        } else {
                            "$years year ago"
                        }
                    } else {
                        return dayName + " " + dayNum + " " + monthName + " " + year + " " +
                                hour + ":" + min + ":" + sec
                    }

                } else {
                    return null
                }
            } else {
                return null
            }
        }
    }
}