package com.choota.dmotion.presentation.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choota.dmotion.domain.model.Channel
import com.choota.dmotion.domain.model.ChannelPage
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.use_case.get_channels.GetChannelsUseCase
import com.choota.dmotion.domain.use_case.get_images.GetImagesUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.GetVideosUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.InsertVideoUseCase
import com.choota.dmotion.util.Constants.RESOURCE_VIDEOS
import com.choota.dmotion.util.Resource
import com.choota.dmotion.util.resolve
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val channelsUseCase: GetChannelsUseCase,
    private val imagesUseCase: GetImagesUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val insertVideoUseCase: InsertVideoUseCase,
) :
    ViewModel() {

    // mutable state for api response
    private val _channelState = MutableStateFlow<ChannelDataState>(ChannelDataState())
    val channelState: StateFlow<ChannelDataState> = _channelState

    init {
        getChannels(1)

        // get resource videos and insert if empty
        getVideosUseCase().onEach {
            if(it.isEmpty()){
                RESOURCE_VIDEOS.forEach { link ->
                    insertVideoUseCase(
                        ResourceVideo(0, link)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * get channels as user scroll through
     * @param page is the page number
     */
    private fun getChannels(page: Int = 1) {
        channelsUseCase(page).onEach { _it ->
            when (_it) {
                is Resource.Error -> {
                    _channelState.value = ChannelDataState(
                        isLoading = false,
                        error = _it.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _channelState.value = ChannelDataState(isLoading = true)
                }
                is Resource.Success -> {
                    getImages(_it.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getImages(page: ChannelPage) {
        viewModelScope.launch(Dispatchers.IO) {
            val _list = mutableListOf<Channel>()
            val job = page.list.map {
                async {
                    imagesUseCase(it.id, it.id).onEach { response ->
                        when (response) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _list.add(it.apply {
                                    if (response.data?.list!!.isNotEmpty())
                                        image =
                                            response.data.list[(0 until response.data.list.size).random()].webformatURL.resolve()
                                })
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }.awaitAll()

            job.joinAll()
            withContext(Dispatchers.Main){
                _channelState.value = ChannelDataState(isLoading = false, data = page.apply { list = _list })
            }
        }
    }
}