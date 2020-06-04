package com.example.news.connection

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.news.R
import kotlin.math.acos

class LoadingDiaLog(val activity: Activity) {

    private var alertDialog : AlertDialog?= null

    fun loadingalertDialog(){

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_screen,null))
        builder.setCancelable(true)

        alertDialog = builder.create()
        alertDialog?.show()
        alertDialog?.window?.setBackgroundDrawable(null)
    }
    fun dismissDialog(){
        alertDialog?.dismiss()
    }
}