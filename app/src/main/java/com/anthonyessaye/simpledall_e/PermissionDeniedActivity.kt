package com.anthonyessaye.simpledall_e

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess

class PermissionDeniedActivity : AppCompatActivity() {
    private lateinit var permissionDeniedTextView: TextView
    private lateinit var okayBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_denied)

        permissionDeniedTextView = findViewById(R.id.permissionDeniedTextView)
        okayBtn = findViewById(R.id.okayBtn)

        permissionDeniedTextView.text = "Since you do not agree to the previous statements, you can not move any further. " +
                "Click Okay to close the app or click back if you changed your mind."

        okayBtn.setOnClickListener {
            finishAffinity()
        }
    }
}