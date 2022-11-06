package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NoticeActivity : AppCompatActivity() {
    private lateinit var denyBtn: Button
    private lateinit var acceptBtn: Button
    private lateinit var noticeInfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        noticeInfoTextView = findViewById(R.id.noticeInfoTextView)
        acceptBtn = findViewById(R.id.acceptBtn)
        denyBtn = findViewById(R.id.denyBtn)

        noticeInfoTextView.text = "- This application is a 3rd party implementation that calls APIs originally developed by OpenAI's Dall-E\n\n" +
                "- This software comes with no assurances or insurances\n\n" +
                "- This software is in no way associated or affiliated with OpenAI\n\n" +
                "- The developer of this software holds no responsibility over its use"

        acceptBtn.setOnClickListener {
            val noticeIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(noticeIntent)
        }

        denyBtn.setOnClickListener {
            val noticeIntent = Intent(this, PermissionDeniedActivity::class.java)
            startActivity(noticeIntent)
        }
    }
}