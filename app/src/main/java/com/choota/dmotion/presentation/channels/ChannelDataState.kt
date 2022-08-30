package com.choota.dmotion.presentation.channels

import com.choota.dmotion.domain.model.ChannelPage

/**
 * State for popular channel list
 */
data class ChannelDataState(
    var isLoading: Boolean = false,
    var data: ChannelPage = ChannelPage(),
    var error: String = ""
)
