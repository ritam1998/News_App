package com.example.news.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.news.MainActivity
import com.example.news.R

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_TIMEOUT : Long = 3000
    private var handler : Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler?.postDelayed(Runnable {
            startActivity(Intent(this,MainActivity :: class.java))
            finish()
        },SPLASH_SCREEN_TIMEOUT)
    }
}
