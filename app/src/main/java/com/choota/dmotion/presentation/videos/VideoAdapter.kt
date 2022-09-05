package com.choota.dmotion.presentation.videos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.choota.dmotion.R
import com.choota.dmotion.domain.model.Video
import com.choota.dmotion.presentation.videodetails.VideoDetailActivity
import com.choota.dmotion.util.Constants.DETAILS
import com.choota.dmotion.util.launchActivity
import com.choota.dmotion.util.resolve
import com.choota.dmotion.util.resolveHtml

/**
 * Adapter to populate Videos coming from dailymotion API
 * @param context is the context of activity where it creates
 */
class VideoAdapter(loader: ImageLoader, context: Context, private val callback: (Video) -> Unit) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    val _loader = loader
    val _context = context
    var items: List<Video> = listOf()

    fun addVideos(data: List<Video>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(_context).inflate(R.layout.cell_video_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.txtTitle.text = item.title.resolve()
        holder.txtDescription.text = item.description.resolveHtml()
        holder.txtViews.text = "${item.viewsTotal} views"

        val request = ImageRequest.Builder(_context)
            .data(item.thumbnail720Url)
            .placeholder(R.drawable.placeholder)
            .crossfade(true)
            .target(holder.imgPoster)
            .build()

        _loader.enqueue(request)

        holder.layMain.setOnClickListener {
            callback(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgPoster: AppCompatImageView = view.findViewById(R.id.imgPoster)
        var txtTitle: AppCompatTextView = view.findViewById(R.id.txtTitle)
        var txtDescription: AppCompatTextView = view.findViewById(R.id.txtDescription)
        var txtViews: AppCompatTextView = view.findViewById(R.id.txtViews)
        var layMain: LinearLayoutCompat = view.findViewById(R.id.layMain)
    }
}