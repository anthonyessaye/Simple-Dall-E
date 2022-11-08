package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.anthonyessaye.simpledall_e.Database.DatabaseManager

class PreloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_preload)

        // Add artificial delay to show logo
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (DatabaseManager.Read.getAppSettings() == null) {
                    val noticeIntent = Intent(this, NoticeActivity::class.java)
                    startActivity(noticeIntent)
                }

                else {
                    val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)
                }
            },
            3000
        )
    }
}