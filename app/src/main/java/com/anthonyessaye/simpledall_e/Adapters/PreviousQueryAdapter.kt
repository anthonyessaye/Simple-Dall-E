package com.anthonyessaye.simpledall_e.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import com.anthonyessaye.simpledall_e.R
import com.bumptech.glide.Glide

class PreviousQueryAdapter(private val mList: List<PreviousQuery>,
                           private val allImages: List<Image>) : RecyclerView.Adapter<PreviousQueryAdapter.ViewHolder>() {

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
        val count = allImages.filter { image -> image.parentQueryID == mList[position].queryID }.count()

        holder.titleTextView.text = "\"" + queryString + "\""
        holder.countTextView.text = holder.countTextView.text.toString() + " " + count
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