package com.choota.dmotion.presentation.videos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.choota.dmotion.R
import com.choota.dmotion.domain.model.Video
import com.choota.dmotion.util.resolve

/**
 * Adapter to populate Videos coming from dailymotion API
 * @param context is the context of activity where it creates
 */
class VideoAdapter(context: Context) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
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
        holder.txtDescription.text = item.description.resolve()
        holder.txtViews.text = "${item.viewsTotal} views"

        holder.imgPoster.load(item.thumbnail720Url){
            crossfade(true)
            placeholder(R.drawable.placeholder)
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
    }
}