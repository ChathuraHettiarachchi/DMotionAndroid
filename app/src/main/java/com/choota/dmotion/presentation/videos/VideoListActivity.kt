package com.choota.dmotion.presentation.videos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityVideoListBinding
import com.choota.dmotion.domain.model.Video
import com.choota.dmotion.presentation.common.dialog.NetworkStateDialog
import com.choota.dmotion.presentation.videodetails.VideoDetailActivity
import com.choota.dmotion.util.*
import com.choota.dmotion.util.Constants.CHANNEL
import com.choota.dmotion.util.Constants.DESCRIPTION
import com.choota.dmotion.util.Constants.IMAGE
import com.choota.dmotion.util.Constants.TITLE
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoListActivity : AppCompatActivity() {

    @Inject
    lateinit var loader: ImageLoader

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var channel: String
    private lateinit var imagePoster: String
    private lateinit var title: String
    private lateinit var description: String

    private var isConnected: Boolean = true
    private var _binding: ActivityVideoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
        initUI()
    }

    private fun setup() {
        videoAdapter = VideoAdapter(loader, this) {
            launchVideoDetails(it)
        }

        channel = intent.getStringExtra(CHANNEL).toString()
        imagePoster = intent.getStringExtra(IMAGE).toString()
        title = intent.getStringExtra(TITLE).toString()
        description = intent.getStringExtra(DESCRIPTION).toString()

        viewModel.getVideos(channel)

        lifecycleScope.launch {
            isInternetConnectedFlow.collectLatest { state ->
                isConnected = state
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.videoState.collect {
                if (it.isLoading) {
                    binding.viewShimmer.startShimmer()
                    binding.recyclerVideos.gone()
                } else if (!it.isLoading && it.error.isNotEmpty()) {
                    if (isConnected)
                        toast(it.error)
                    else
                        NetworkStateDialog.show(this@VideoListActivity)
                } else {
                    binding.viewShimmer.stopShimmer()
                    binding.viewShimmer.gone()
                    binding.recyclerVideos.visible()
                    videoAdapter.addVideos(it.data.list)
                }
            }
        }
    }

    private fun launchVideoDetails(it: Video) {
        if (isConnected)
            launchActivity<VideoDetailActivity> {
                putExtra(Constants.DETAILS, it)
            }
        else
            NetworkStateDialog.show(this)
    }

    private fun initUI() {
        binding.recyclerVideos.apply {
            adapter = videoAdapter
            setHasFixedSize(false)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgPoster.load(imagePoster) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
        }

        binding.txtTitleVideoList.text = title
        binding.txtDescription.text = description
    }
}