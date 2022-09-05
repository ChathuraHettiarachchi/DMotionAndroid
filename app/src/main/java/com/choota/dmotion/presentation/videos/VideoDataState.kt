package com.choota.dmotion.presentation.videos

import com.choota.dmotion.domain.model.VideoPage

/**
 * State for popular video list
 */
data class VideoDataState(
    var isLoading: Boolean = false,
    var data: VideoPage = VideoPage(),
    var error: String = ""
)
