package com.choota.dmotion.presentation.videodetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityVideoDetailBinding
import com.choota.dmotion.domain.model.Video
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.presentation.player.PlayerActivity
import com.choota.dmotion.util.*
import com.choota.dmotion.util.Constants.DETAILS
import com.choota.dmotion.util.Constants.PLAYBACK_RESULT_CODE
import com.choota.dmotion.util.Constants.RESOURCE_VIDEO
import com.choota.dmotion.util.Constants.RESOURCE_VIDEO_CALLBACK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoDetailActivity : AppCompatActivity() {

    private lateinit var video: Video

    private val viewModel: VideoDetailsViewModel by viewModels()
    private var _binding: ActivityVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
        initUI()
    }

    private fun setup() {
        video = intent.getParcelableExtra<Video>(DETAILS)!!
        viewModel.getVideoSuggestion()
    }

    private fun initUI() {
        binding.txtTitle.text = video.title.resolve()
        binding.txtDescription.text = video.description.resolveHtml()
        binding.txtViews.text = video.viewsTotal.toString().resolve()
        binding.txtViews.text = video.viewsTotal.toString().resolve()
        binding.txtDuration.text = video.duration.toMMSS()
        binding.txtCountry.text = video.country.country()
        binding.txtLanguage.text = video.language.language()
        binding.btnBack.setOnClickListener { finish() }

        binding.imgPoster.load(video.thumbnail720Url) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
        }

        binding.fabPlay.setOnClickListener {
            val intent = Intent(this@VideoDetailActivity, PlayerActivity::class.java)
            intent.putExtra(RESOURCE_VIDEO, viewModel.selectedVideo)
            getPlayTime.launch(intent)
        }

        if(!video.tags.isNullOrEmpty()) {
            val chipsLayoutManager =
                ChipsLayoutManager.newBuilder(this)
                    .setChildGravity(Gravity.TOP)
                    .setScrollingEnabled(true)
                    .setGravityResolver { Gravity.CENTER }
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_VIEW)
                    .build()

            binding.recyclerTags.apply {
                adapter = VideoTagAdapter(this@VideoDetailActivity, video.tags!! as List<String>)
                layoutManager = chipsLayoutManager
                setHasFixedSize(true)
                addItemDecoration(SpacingItemDecoration(12, 12))
            }
        } else {
            binding.txtTag.gone()
        }
    }

    private val getPlayTime = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == PLAYBACK_RESULT_CODE){
            val value = it.data?.getParcelableExtra<ResourceVideo>(RESOURCE_VIDEO_CALLBACK)
            viewModel.updatePlaytime(value!!)
        }
    }
}