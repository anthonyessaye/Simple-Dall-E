package com.anthonyessaye.simpledall_e.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.anthonyessaye.simpledall_e.Constants.TagConstants
import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import com.anthonyessaye.simpledall_e.R
import com.anthonyessaye.simpledall_e.ResultsActivity
import com.bumptech.glide.Glide

class PreviousQueryAdapter(private val mList: List<PreviousQuery>,
                           private val allImages: List<Image>,
                           private val context: Context) : RecyclerView.Adapter<PreviousQueryAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val queryString = mList[position].queryText
        val relatedImages = allImages.filter { image -> image.parentQueryID == mList[position].queryID }

        holder.titleTextView.text = "\"" + queryString + "\""
        holder.countTextView.text = holder.countTextView.text.toString() + " " + relatedImages.count()

        holder.itemView.setOnClickListener {
            val resultIntent = Intent(context, ResultsActivity::class.java)
            var imageArrayList = arrayListOf<Image>()
            imageArrayList.addAll(relatedImages)

            resultIntent.putParcelableArrayListExtra(TagConstants.INTENT_IMAGE_KEY, imageArrayList)
            context.startActivity(resultIntent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val countTextView: TextView = itemView.findViewById(R.id.itemCountTextView)
    }
}