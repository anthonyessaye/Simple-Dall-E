package com.anthonyessaye.simpledall_e

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
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
    private lateinit var loaderLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)

        generateBtn = findViewById(R.id.generateBtn)
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine)
        numberOfImagesNumberPicker = findViewById(R.id.numberOfImagesNumberPicker)
        radioGroup = findViewById(R.id.radioGroup)
        loaderLayout = findViewById(R.id.loaderLayout)

        numberOfImagesNumberPicker.minValue = 1
        numberOfImagesNumberPicker.maxValue = 10

        generateBtn.setOnClickListener {

            val imagesToGenerate = numberOfImagesNumberPicker.value
            val queryString = editTextTextMultiLine.text.toString()
            val selectedRadioButton = radioGroup.checkedRadioButtonId - 1

            if (!queryString.isNullOrEmpty() && selectedRadioButton >= 0) {
                val resolution = ImageSize.fromInt(selectedRadioButton)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Information")
                alertDialogBuilder.setMessage("The process of generating these images cost money. " +
                        "This transaction will occur directly on your API usage with OpenAI and not " +
                        "with the creator of this application. Generation time will depend heavily " +
                        "on the chosen resolution and number of pictures to generate.\n\n" +
                        "Here is a general breakdown:\n\n" +
                        "Chosen Query: ${queryString}\n" +
                        "Number Of Images: ${imagesToGenerate}\n" +
                        "Chosen Resolution: ${resolution.resolution}x${resolution.resolution}\n" +
                        "Total Cost: $${calculateCost(imagesToGenerate, resolution)}")

                alertDialogBuilder.setPositiveButton("Go") { dialog, which ->

                    runOnUiThread { loaderLayout.visibility = View.VISIBLE }
                    APICalls.POST.generateImages(queryString, numberOfImages = imagesToGenerate, resolution) { success, urlArrayList ->
                        if (success) {
                            runOnUiThread {
                                val resultIntent = Intent(this, ResultsActivity::class.java)
                                val query = DatabaseManager.Write.setPreviousQuery(
                                    queryString,
                                    ImageSize.small
                                )

                                val imageArray = createImagesFromURLs(urlArrayList!!, query.queryID)

                                resultIntent.putStringArrayListExtra(
                                    TagConstants.INTENT_IMAGE_KEY,
                                    imageArray
                                )

                                loaderLayout.visibility = View.GONE
                                startActivity(resultIntent)
                            }
                        } else {
                            Log.i(TagConstants.TAG, "API Call Failed")
                        }
                    }

                }

                alertDialogBuilder.setNeutralButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                alertDialogBuilder.show()
            }

            else {
                Toast.makeText(this, "Check if all fields are correct", Toast.LENGTH_LONG).show()
            }
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