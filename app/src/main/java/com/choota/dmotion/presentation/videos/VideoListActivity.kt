package com.choota.dmotion.presentation.videos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityVideoListBinding
import com.choota.dmotion.util.Constants.CHANNEL
import com.choota.dmotion.util.Constants.DESCRIPTION
import com.choota.dmotion.util.Constants.IMAGE
import com.choota.dmotion.util.Constants.TITLE
import com.choota.dmotion.util.gone
import com.choota.dmotion.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoListActivity : AppCompatActivity() {

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var channel: String
    private lateinit var imagePoster: String
    private lateinit var title: String
    private lateinit var description: String

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
        channel = intent.getStringExtra(CHANNEL).toString()
        imagePoster = intent.getStringExtra(IMAGE).toString()
        title = intent.getStringExtra(TITLE).toString()
        description = intent.getStringExtra(DESCRIPTION).toString()
        videoAdapter = VideoAdapter(this)
        viewModel.getVideos(channel)

        lifecycleScope.launchWhenCreated {
            viewModel.videoState.collect {
                if (it.isLoading) {
                    binding.recyclerVideos.gone()
                } else if (!it.isLoading && it.error.isNotEmpty()) {
                    Toast.makeText(this@VideoListActivity, it.error, Toast.LENGTH_LONG).show()
                } else {
                    binding.recyclerVideos.visible()
                    videoAdapter.addVideos(it.data.list)
                }
            }
        }
    }

    private fun initUI() {
        binding.recyclerVideos.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(this@VideoListActivity)
            setHasFixedSize(false)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgPoster.load(imagePoster) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
        }

        binding.txtTitle.text = title
        binding.txtDescription.text = description
    }
}