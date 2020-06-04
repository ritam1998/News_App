package com.example.news.World.WorldModel

import android.os.Parcel
import android.os.Parcelable

class WorldHeadLineModel(
    var worldHeadlineTitle: String?,
    var worldHeadLinePublishTime: String?,
    var headlineImage: String?,
    var headLineAuthor: String?,
    var headLineNewsJournal: String?,
    var headLineNewsContent: String?,
    var headLineNewsUrl: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(worldHeadlineTitle)
        parcel.writeString(worldHeadLinePublishTime)
        parcel.writeString(headlineImage)
        parcel.writeString(headLineAuthor)
        parcel.writeString(headLineNewsJournal)
        parcel.writeString(headLineNewsContent)
        parcel.writeString(headLineNewsUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorldHeadLineModel> {
        override fun createFromParcel(parcel: Parcel): WorldHeadLineModel {
            return WorldHeadLineModel(parcel)
        }

        override fun newArray(size: Int): Array<WorldHeadLineModel?> {
            return arrayOfNulls(size)
        }
    }
}