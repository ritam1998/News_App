package com.example.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.news.R

class WebViewActivity : AppCompatActivity() {

    private var webView : WebView? = null
    private var webViewProgress : ProgressBar? = null
    private var webViewFrame : FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webViewProgress = findViewById(R.id.webViewProgress)
        webViewFrame = findViewById(R.id.webViewFrameLayout)
        webViewProgress?.max = 100

        val worldNewsFullContentBundle = intent?.extras
        val worldNewsFullContent = worldNewsFullContentBundle?.getString("worldNewsFullContent")
        Log.e("webview","$worldNewsFullContent")
        loadWebPage(worldNewsFullContent)

        val worldTrendingNewsFullContent = intent?.extras?.getString("worldTrendingNewsFullContent")
        loadWebPage(worldTrendingNewsFullContent)
    }
    private fun loadWebPage(weburl : String?){

        webView = findViewById(R.id.webview)

        webView?.webViewClient = HelpClient(weburl)

        webView?.webChromeClient = object : WebChromeClient(){

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                webViewFrame?.visibility = View.VISIBLE
                webViewProgress?.setProgress(newProgress)

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

                val input = view?.originalUrl
                val originalUrl = input?.substring(0,19)
                setTitle(originalUrl)

                if(newProgress ==  100){
                    webViewFrame?.visibility = View.GONE
                    setTitle(view?.title)
                }
            }
        }
        webView?.settings?.javaScriptEnabled = true
        webView?.isVerticalScrollBarEnabled = false
        webView?.loadUrl(weburl)
        webViewProgress?.setProgress(0)
    }

    inner class HelpClient(var url : String?) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?,request: WebResourceRequest?): Boolean {
            view?.loadUrl(url)
            webViewFrame?.visibility = View.VISIBLE
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView?.canGoBack()!!){
                webView?.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}
