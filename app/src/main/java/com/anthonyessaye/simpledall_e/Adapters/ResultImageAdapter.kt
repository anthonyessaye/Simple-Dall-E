package com.anthonyessaye.simpledall_e.Adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ResultImageAdapter(private val mList: List<String>) : RecyclerView.Adapter<ResultImageAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result_image, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = mList[position]

        Glide.with(holder.imageView).load(url)
            .centerCrop()
            .error(R.mipmap.ic_na)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    holder.progressBar.setVisibility(View.GONE)
                    holder.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    holder.imageView.setImageResource(R.drawable.not_available)
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    holder.progressBar.setVisibility(View.GONE)
                    holder.imageView.setImageDrawable(resource)
                    return true
                }

            })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val progressBar: ProgressBar = itemView.findViewById(R.id.itemProgressBar)
    }
}