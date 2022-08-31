package com.choota.dmotion.presentation.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choota.dmotion.domain.use_case.get_channel_videos.GetChannelVideosUseCase
import com.choota.dmotion.domain.use_case.get_channels.GetChannelsUseCase
import com.choota.dmotion.presentation.channels.ChannelDataState
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videosUseCase: GetChannelVideosUseCase) :
    ViewModel() {

    // mutable state for api response
    private val _videoState = MutableStateFlow<VideoDataState>(VideoDataState())
    val videoState: StateFlow<VideoDataState> = _videoState

    /**
     * get channels as user scroll through
     * @param channel is the selected channel id
     * @param page is the page number
     * @param limit is the number of data per page
     */
    fun getVideos(channel: String, page: Int = 1, limit: Int = 50) {
        videosUseCase(channel, page, limit, Constants.VIDEO_FIELDS).onEach {
            when (it) {
                is Resource.Error -> {
                    _videoState.value = VideoDataState(
                        isLoading = false,
                        error = it.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _videoState.value = VideoDataState(isLoading = true)
                }
                is Resource.Success -> {
                    _videoState.value = VideoDataState(isLoading = false, data = it.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}