package com.example.news.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class NetworkChangeReceiver : BroadcastReceiver() {

    private var connectionChangeCallback : ConnectionChangeCallback? = null

    fun setConnectionChangeCallback(connectionChangeCallback: ConnectionChangeCallback){
        this.connectionChangeCallback = connectionChangeCallback
    }
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.e("check network","${context?.let { isNetworkAvailable(it) }}")
        context?.let { isNetworkAvailable(it) }?.let {
            connectionChangeCallback?.onConnectionChange(it)
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
interface ConnectionChangeCallback {
    fun onConnectionChange(isConnected : Boolean)
}
