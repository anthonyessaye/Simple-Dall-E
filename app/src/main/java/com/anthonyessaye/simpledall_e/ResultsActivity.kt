package com.anthonyessaye.simpledall_e

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyessaye.simpledall_e.Adapters.ResultImageAdapter
import com.anthonyessaye.simpledall_e.Constants.TagConstants
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import io.realm.kotlin.types.ObjectId

class ResultsActivity : AppCompatActivity() {
    private lateinit var resultsReyclerView: RecyclerView
    var imageArray = ArrayList<Image>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        imageArray = intent.extras?.getStringArrayList(TagConstants.INTENT_IMAGE_KEY) as ArrayList<Image>
        resultsReyclerView = findViewById(R.id.resultsReyclerView)

        resultsReyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ResultImageAdapter(imageArray)
        resultsReyclerView.adapter = adapter
    }
}