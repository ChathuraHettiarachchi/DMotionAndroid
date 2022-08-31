package com.choota.dmotion.presentation.videodetails

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
import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.presentation.videos.VideoListActivity
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Constants.CHANNEL
import com.choota.dmotion.util.Constants.DESCRIPTION
import com.choota.dmotion.util.Constants.IMAGE
import com.choota.dmotion.util.Constants.TITLE
import com.choota.dmotion.util.launchActivity
import com.choota.dmotion.util.resolve
import com.choota.dmotion.util.resolveHtml

/**
 * Adapter to populate tags coming from dailymotion API
 * @param context is the context of activity where it creates
 * @param data is the tag list of activity where it creates
 */
class VideoTagAdapter(context: Context, data: List<String>) : RecyclerView.Adapter<VideoTagAdapter.ViewHolder>() {
    private val _context = context
    private val items: List<String> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(_context).inflate(R.layout.cell_tag, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.txtTag.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtTag: AppCompatTextView = view.findViewById(R.id.txtTag)
    }
}