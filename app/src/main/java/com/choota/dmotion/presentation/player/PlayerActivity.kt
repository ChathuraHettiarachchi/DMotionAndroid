package com.choota.dmotion.presentation.player

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import com.choota.dmotion.databinding.ActivityPlayerBinding
import com.choota.dmotion.util.gone
import com.choota.dmotion.util.visible

class PlayerActivity : AppCompatActivity() {

    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        val uri: Uri = Uri.parse("https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        var controller = MediaController(this)

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
        binding.btnClose.setOnClickListener { finish() }
    }
}