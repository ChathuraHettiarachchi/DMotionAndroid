package com.choota.dmotion.presentation.channels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.text.htmlEncode
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import com.choota.dmotion.R
import com.choota.dmotion.databinding.ActivityChannelBinding
import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.presentation.common.dialog.NetworkStateDialog
import com.choota.dmotion.presentation.videos.VideoListActivity
import com.choota.dmotion.util.*
import com.rommansabbir.networkx.extension.isInternetConnectedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChannelActivity : AppCompatActivity() {

    @Inject
    lateinit var loader: ImageLoader

    private lateinit var channelAdapter: ChannelAdapter
    private var isConnected: Boolean = true
    private var isInitialLoadSuccess: Boolean = true
    private var _binding: ActivityChannelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
        EspressoIdlingResource.increment()
        lifecycleScope.launch {
            isInternetConnectedFlow.collectLatest { state ->
                isConnected = if(Constants.isTestMode()){
                    true
                } else {
                    state
                }
                if(!isInitialLoadSuccess) viewModel.getChannels()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.channelState.collect {
                if (it.isLoading) {
                    binding.viewShimmer.startShimmer()
                } else if (!it.isLoading && it.error.isNotEmpty()) {
                    isInitialLoadSuccess = false
                    if (isConnected)
                        toast(it.error)
                    else
                        NetworkStateDialog.show(this@ChannelActivity)
                } else {
                    EspressoIdlingResource.decrement()
                    isInitialLoadSuccess = true

                    binding.viewShimmer.stopShimmer()
                    binding.viewShimmer.gone()
                    binding.appBar.visible()
                    binding.recyclerChannels.visible()

                    EspressoIdlingResource.increment()
                    populateChannels(it.data.list.toMutableList())
                }
            }
        }
    }

    private fun initUI() {
        channelAdapter = ChannelAdapter(loader, this) {
            launchVideoList(it)
        }

        binding.recyclerChannels.apply {
            adapter = channelAdapter
            setHasFixedSize(true)
        }

        binding.viewShimmer.visible()
    }

    private fun launchVideoList(it: Channel) {
        if (isConnected)
            launchActivity<VideoListActivity> {
                putExtra(Constants.CHANNEL, it.id)
                putExtra(Constants.IMAGE, it.image)
                putExtra(Constants.TITLE, it.name)
                putExtra(Constants.DESCRIPTION, it.description)
            }
        else
            NetworkStateDialog.show(this)
    }

    private fun populateChannels(items: MutableList<Channel>) {
        val first = items.removeAt(0)
        binding.txtFirstTitle.text = first.name.resolve()
        binding.txtFirstDescription.text = first.description.resolveHtml()

        binding.imgPoster.setOnClickListener {
            launchVideoList(first)
        }

        binding.imgPoster.load(first.image) {
            placeholder(R.drawable.placeholder)
            crossfade(true)
        }

        channelAdapter.notifyDataSetChanged()
        channelAdapter.addChannels(items)
        EspressoIdlingResource.decrement()
    }
}