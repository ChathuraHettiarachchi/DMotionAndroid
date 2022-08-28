package com.choota.dmotion.domain.repository

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto

interface DMotionRepository {
    suspend fun getChannels(page: Int): ChannelDto
    suspend fun getChannelVideos(
        channel: String,
        page: Int,
        limit: Int,
        fields: String
    ): VideoDto
}