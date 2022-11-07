package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.anthonyessaye.simpledall_e.APIs.APICalls
import com.anthonyessaye.simpledall_e.Constants.TagConstants
import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import com.anthonyessaye.simpledall_e.Enumerations.ImageSize
import io.realm.kotlin.types.ObjectId

class GenerateActivity : AppCompatActivity() {
    private lateinit var generateBtn: Button
    private lateinit var editTextTextMultiLine: EditText
    private lateinit var numberOfImagesNumberPicker: NumberPicker
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        generateBtn = findViewById(R.id.generateBtn)
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine)
        numberOfImagesNumberPicker = findViewById(R.id.numberOfImagesNumberPicker)
        radioGroup = findViewById(R.id.radioGroup)

        numberOfImagesNumberPicker.minValue = 1
        numberOfImagesNumberPicker.maxValue = 10

        generateBtn.setOnClickListener {

            val imagesToGenerate = numberOfImagesNumberPicker.value
            val query = editTextTextMultiLine.text.toString()
            val selectedRadioButton = radioGroup.checkedRadioButtonId - 1

            if (!query.isNullOrEmpty() && selectedRadioButton >= 0) {
                val resolution = ImageSize.fromInt(selectedRadioButton)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Information")
                alertDialogBuilder.setMessage("The process of generating these images cost money. " +
                        "This transaction will occur directly on your API usage with OpenAI and not " +
                        "with the creator of this application.\n\n" +
                        "Here is a general breakdown:\n\n" +
                        "Chosen Query: ${query}\n" +
                        "Number Of Images: ${imagesToGenerate}\n" +
                        "Chosen Resolution: ${resolution.resolution}x${resolution.resolution}\n" +
                        "Total Cost: $${calculateCost(imagesToGenerate, resolution)}")

                alertDialogBuilder.setPositiveButton("Go") { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNeutralButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                alertDialogBuilder.show()
            }

            else {
                Toast.makeText(this, "Check if all fields are correct", Toast.LENGTH_LONG).show()
            }

           /* val queryString = "dall-e"

            APICalls.POST.generateImages(queryString, numberOfImages = 1, ImageSize.small) { success, urlArrayList ->
                if (success) {
                    runOnUiThread {
                        val resultIntent = Intent(this, ResultsActivity::class.java)
                        val query = DatabaseManager.Write.setPreviousQuery(
                            queryString,
                            ImageSize.small.ordinal
                        )

                        val imageArray = createImagesFromURLs(urlArrayList!!, query.queryID)

                        resultIntent.putStringArrayListExtra(
                            TagConstants.INTENT_IMAGE_KEY,
                            imageArray
                        )
                        startActivity(resultIntent)
                    }
                } else {
                    Log.i(TagConstants.TAG, "API Call Failed")
                }
            } */
        }
    }

    fun createImagesFromURLs(urlList: ArrayList<String>, queryID: ObjectId): ArrayList<String> {
        var imageArrayList: ArrayList<String> = arrayListOf()

        for (url in urlList) {
            DatabaseManager.Write.setImage(url, queryID)
            imageArrayList.add(url)
        }

        return imageArrayList
    }

    fun calculateCost(numberOfImages: Int, imageSize: ImageSize): Double {
        return numberOfImages * imageSize.cost
    }
}