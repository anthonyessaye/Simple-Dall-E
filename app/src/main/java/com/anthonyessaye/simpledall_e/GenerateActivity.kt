package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.anthonyessaye.simpledall_e.Constants.TagConstants
import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import io.realm.kotlin.types.ObjectId

class GenerateActivity : AppCompatActivity() {
    private lateinit var generateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        generateBtn = findViewById(R.id.generateBtn)


        generateBtn.setOnClickListener {
            val queryString = "Taylor swift doing things"


            val resultIntent = Intent(this, ResultsActivity::class.java)
            val imageURLStrings = arrayListOf("https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg",
                "https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg",
                "https://www.rollingstone.com/wp-content/uploads/2022/10/taylor-swift-3am.jpg")

            val query = DatabaseManager.Write.setPreviousQuery(queryString)

            val imageArray = createImagesFromURLs(imageURLStrings, query.queryID)

            resultIntent.putParcelableArrayListExtra(TagConstants.INTENT_IMAGE_KEY, imageArray)
            startActivity(resultIntent)
        }
    }

    fun createImagesFromURLs(urlList: ArrayList<String>, queryID: ObjectId): ArrayList<Image> {
        var imageArrayList: ArrayList<Image> = arrayListOf()

        for (url in urlList) {
            imageArrayList.add(DatabaseManager.Write.setImage(url, queryID))
        }

        return imageArrayList
    }
}