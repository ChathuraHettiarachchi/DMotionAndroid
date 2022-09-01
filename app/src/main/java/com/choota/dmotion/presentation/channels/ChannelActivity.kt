package com.choota.dmotion.presentation.channels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.text.htmlEncode
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityChannelBinding
import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.presentation.videos.VideoListActivity
import com.choota.dmotion.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChannelActivity : AppCompatActivity() {

    @Inject lateinit var loader: ImageLoader

    private lateinit var channelAdapter: ChannelAdapter
    private var _binding: ActivityChannelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.channelState.collect{
                if(it.isLoading){
                    binding.viewShimmer.startShimmer()
                } else if (!it.isLoading && it.error.isNotEmpty()){
                    Toast.makeText(this@ChannelActivity, it.error, Toast.LENGTH_LONG).show()
                } else {
                    binding.viewShimmer.stopShimmer()
                    binding.viewShimmer.gone()
                    binding.appBar.visible()
                    binding.recyclerChannels.visible()

                    populateChannels(it.data.list.toMutableList())
                }
            }
        }
    }

    private fun initUI() {
        channelAdapter = ChannelAdapter(loader, this)
        binding.viewShimmer.visible()

        binding.recyclerChannels.apply {
            adapter = channelAdapter
            layoutManager = GridLayoutManager(this@ChannelActivity, 2)
            setHasFixedSize(true)
        }
    }

    private fun populateChannels(items: MutableList<Channel>){
        val first = items.removeAt(0)
        binding.imgPoster.load(first.image){
            placeholder(R.drawable.placeholder)
            crossfade(true)
        }
        binding.txtFirstTitle.text = first.name.resolve()
        binding.txtFirstDescription.text = first.description.htmlEncode()
        binding.imgPoster.setOnClickListener {
            launchActivity<VideoListActivity> {
                putExtra(Constants.CHANNEL, first.id)
                putExtra(Constants.IMAGE, first.image)
                putExtra(Constants.TITLE, first.name)
                putExtra(Constants.DESCRIPTION, first.description)
            }
        }

        channelAdapter.notifyDataSetChanged()
        channelAdapter.addChannels(items)
    }
}