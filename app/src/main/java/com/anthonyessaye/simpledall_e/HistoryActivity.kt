package com.anthonyessaye.simpledall_e

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyessaye.simpledall_e.Adapters.PreviousQueryAdapter
import com.anthonyessaye.simpledall_e.Adapters.ResultImageAdapter
import com.anthonyessaye.simpledall_e.Constants.TagConstants
import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.Image

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyReyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyReyclerView = findViewById(R.id.historyReyclerView)

        val queryArray = DatabaseManager.Read.getPreviousQueries()
        val allImages = DatabaseManager.Read.getImages()

        historyReyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PreviousQueryAdapter(queryArray, allImages)
        historyReyclerView.adapter = adapter
    }
}