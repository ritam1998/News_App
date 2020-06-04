package com.example.news.World.WorldModel

import android.os.Parcel
import android.os.Parcelable

data class WorldTrendingModel(var trendingNewsJournal : String?,
                              var trendingNewsTitle : String?,
                              var trendingNewsAuthor : String?,
                              var trendingNewsUrl : String?,
                              var trendingNewsContent : String?,
                              var trendingImageUrl : String?,
                              var trendingNewsTime : String?,
                              var newsTypeImage : Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(trendingNewsJournal)
        parcel.writeString(trendingNewsTitle)
        parcel.writeString(trendingNewsAuthor)
        parcel.writeString(trendingNewsUrl)
        parcel.writeString(trendingNewsContent)
        parcel.writeString(trendingImageUrl)
        parcel.writeString(trendingNewsTime)
        parcel.writeInt(newsTypeImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorldTrendingModel> {
        override fun createFromParcel(parcel: Parcel): WorldTrendingModel {
            return WorldTrendingModel(parcel)
        }

        override fun newArray(size: Int): Array<WorldTrendingModel?> {
            return arrayOfNulls(size)
        }
    }
}
