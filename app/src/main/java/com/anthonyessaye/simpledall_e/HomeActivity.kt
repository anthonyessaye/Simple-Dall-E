package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    private lateinit var generateBtn: Button
    private lateinit var historyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        historyBtn = findViewById(R.id.historyBtn)
        generateBtn = findViewById(R.id.generateBtn)

        generateBtn.setOnClickListener {
            val generationIntent = Intent(this, GenerateActivity::class.java)
            startActivity(generationIntent)
        }
    }
}