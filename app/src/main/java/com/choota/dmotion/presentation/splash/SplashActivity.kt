package com.choota.dmotion.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.choota.dmotion.R
import com.choota.dmotion.presentation.channels.ChannelActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this@SplashActivity, ChannelActivity::class.java))
        finish()
    }
}