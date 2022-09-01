package com.choota.dmotion.presentation.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.htmlEncode
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Adapter to populate channels coming from dailymotion API
 * @param context is the context of activity where it creates
 */
class ChannelAdapter (loader: ImageLoader, context: Context) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    val _loader = loader
    val _context = context
    var items: List<Channel> = listOf()

    fun addChannels(data: List<Channel>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            LayoutInflater.from(_context).inflate(R.layout.cell_channel_item, parent, false)
        )

        holder.setIsRecyclable(false)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.txtTitle.text = item.name.resolve()
        holder.txtDescription.text = item.description.resolveHtml()

        val request = ImageRequest.Builder(_context)
            .data(item.image)
            .crossfade(true)
            .placeholder(R.drawable.placeholder)
            .target(holder.imgPoster)
            .build()

        _loader.enqueue(request)

        holder.imgPoster.setOnClickListener {
            _context.launchActivity<VideoListActivity> {
                putExtra(CHANNEL, item.id)
                putExtra(IMAGE, item.image)
                putExtra(TITLE, item.name)
                putExtra(DESCRIPTION, item.description)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgPoster: AppCompatImageView = view.findViewById(R.id.imgPoster)
        var txtTitle: AppCompatTextView = view.findViewById(R.id.txtTitle)
        var txtDescription: AppCompatTextView = view.findViewById(R.id.txtDescription)
    }
}