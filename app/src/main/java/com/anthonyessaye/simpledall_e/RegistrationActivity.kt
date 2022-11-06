package com.anthonyessaye.simpledall_e

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationInfoTextView: TextView
    private lateinit var registrationEditText: EditText
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationInfoTextView = findViewById(R.id.registrationInfoTextView)
        registrationEditText = findViewById(R.id.registrationEditText)
        registerBtn = findViewById(R.id.registerBtn)

        registrationInfoTextView.text = "Please note that this application depends on the following: \n\n" +
                "1) You already have a DALL-E account\n" +
                "2) You already have an API key tied to your account\n\n" +
                "Please note that this application will use your own DALL-E account and costs" +
                " will incur on your account. Use this wisely.\n\n" +
                "The creator of this application is NOT responsible for any costs that incur on your account." +
                " This application does not contact any server other than that of OpenAI. All information is recorded locally" +
                " on your device. Thus your API key will be stored safely on your device and no where else!\n\n" +
                "By registering your token you are acknowledging all the facts stated above.\n\n" +
                "To obtain a token please visit the following link: " + getString(R.string.string_open_ai_url) + "\n\n" +
                "Create a new secret. Copy it and save it here. (You might want to save it elsewhere as well because you " +
                "will not be able to view it again."

        registrationInfoTextView.setLinkTextColor(Color.BLUE)

        registerBtn.setOnClickListener {
            val text = registrationEditText.text

            if (!text.isNullOrEmpty()) {
                if (text.length > 45 && text.length < 55) {
                    DatabaseManager.Write.setAppSettings(text.toString())
                    val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)
                }

                else {
                    Toast.makeText(this, getString(R.string.string_api_key_incorrect), Toast.LENGTH_SHORT).show()
                }
            }

            else {
                Toast.makeText(this, getString(R.string.string_warning_api_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }
}