package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.anthonyessaye.simpledall_e.Constants.TagConstants

class GenerateActivity : AppCompatActivity() {
    private lateinit var generateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        generateBtn = findViewById(R.id.generateBtn)


        generateBtn.setOnClickListener {
            val resultIntent = Intent(this, ResultsActivity::class.java)
            resultIntent.putExtra(TagConstants.INTENT_IMAGE_KEY, arrayListOf("https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg",
                "https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg",
            "https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg"))
            startActivity(resultIntent)
        }
    }
}