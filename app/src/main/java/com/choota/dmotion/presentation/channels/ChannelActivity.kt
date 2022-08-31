package com.choota.dmotion.presentation.channels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityChannelBinding
import com.choota.dmotion.util.gone
import com.choota.dmotion.util.visible
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
                    binding.lottieLoading.visible()
                } else if (!it.isLoading && it.error.isNotEmpty()){
                    Toast.makeText(this@ChannelActivity, it.error, Toast.LENGTH_LONG).show()
                } else {
                    binding.lottieLoading.gone()
                    binding.recyclerChannels.visible()
                    channelAdapter.notifyDataSetChanged()
                    channelAdapter.addChannels(it.data.list)
                }
            }
        }
    }

    private fun initUI() {
        channelAdapter = ChannelAdapter(loader, this)
        binding.lottieLoading.visible()

        binding.recyclerChannels.apply {
            adapter = channelAdapter
            layoutManager = LinearLayoutManager(this@ChannelActivity)
            setHasFixedSize(true)
        }
    }
}