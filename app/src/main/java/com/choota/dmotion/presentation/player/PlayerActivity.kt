package com.choota.dmotion.presentation.player

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.choota.dmotion.databinding.ActivityPlayerBinding
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.presentation.common.dialog.NetworkStateDialog
import com.choota.dmotion.util.Constants.PLAYBACK_RESULT_CODE
import com.choota.dmotion.util.Constants.RESOURCE_VIDEO
import com.choota.dmotion.util.Constants.RESOURCE_VIDEO_CALLBACK
import com.choota.dmotion.util.gone
import com.choota.dmotion.util.toast
import com.choota.dmotion.util.visible
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlayerActivity : AppCompatActivity() {

    private lateinit var video: ResourceVideo
    private lateinit var controller: MediaController

    private var isConnected: Boolean = true
    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findResourceVideo()
        setup()
    }

    private fun findResourceVideo() {
        video = intent.getParcelableExtra<ResourceVideo>(RESOURCE_VIDEO)!!
    }

    private fun setup() {
        lifecycleScope.launch {
            isInternetConnectedFlow.collectLatest { state ->
                isConnected = state
                checkAndDisplayConnectionAlert()
            }
        }

        val uri: Uri = Uri.parse(video.link)
        controller = MediaController(this)

        binding.videoView.setVideoURI(uri)
        binding.videoView.keepScreenOn = true
        binding.videoView.setOnPreparedListener { mp ->
            mp!!.setOnBufferingUpdateListener { _, i ->
                Log.d("Buffer", i.toString())
                if (i > 2) {
                    binding.spinKit.gone()
                } else {
                    binding.spinKit.visible()
                }
            }
        }
        binding.videoView.setMediaController(controller)
        binding.videoView.start()

        controller.setAnchorView(binding.videoView)
        binding.btnClose.setOnClickListener { onBackPressed() }
    }

    private suspend fun checkAndDisplayConnectionAlert() {
        if (!isConnected) {
            withContext(Dispatchers.Main) {
                if (!NetworkStateDialog.dialog.isShowing)
                    NetworkStateDialog.show(this@PlayerActivity)
            }
        }
    }

    private fun calculateDurationAndSetResult() {
        binding.videoView.pause()
        video.apply {
            playTime += (binding.videoView.currentPosition / 1000)
        }

        val resultIntent = Intent()
        resultIntent.putExtra(RESOURCE_VIDEO_CALLBACK, video)
        setResult(PLAYBACK_RESULT_CODE, resultIntent)
    }

    override fun onBackPressed() {
        calculateDurationAndSetResult()
        super.onBackPressed()
    }
}