package com.choota.dmotion.presentation.videodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.use_case.get_channel_videos.GetChannelVideosUseCase
import com.choota.dmotion.domain.use_case.get_channels.GetChannelsUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.GetLeastPlayedVideoUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.InsertVideoUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.UpdateVideoUseCase
import com.choota.dmotion.presentation.channels.ChannelDataState
import com.choota.dmotion.presentation.videos.VideoDataState
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val updateVideoUseCase: UpdateVideoUseCase,
    private val getLeastPlayedVideoUseCase: GetLeastPlayedVideoUseCase
) : ViewModel() {

    // no state keeping since I don't need observing
    var selectedVideo: ResourceVideo? = null

    /**
     * get least played video from db
     */
    fun getVideoSuggestion() {
        viewModelScope.launch(Dispatchers.IO){
            getLeastPlayedVideoUseCase().also {
                selectedVideo = it
            }
        }
    }

    fun updatePlaytime(video: ResourceVideo){
        viewModelScope.launch(Dispatchers.IO){
            updateVideoUseCase(video)
            getVideoSuggestion()
        }
    }
}