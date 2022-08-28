package com.choota.dmotion.data.repository

import com.choota.dmotion.data.remote.DMotionAPI
import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.util.Constants
import javax.inject.Inject

/**
 * DMotionAPI implementation from domain repository. This implements the actions defined in the
 * repo of domain package.
 * Reason is to support great scalability.
 */
class DMotionRepositoryImpl @Inject constructor(private val api: DMotionAPI) : DMotionRepository {

    /**
     * Get channels
     */
    override suspend fun getChannels(page: Int): ChannelDto {
        return api.getChannels(page)
    }

    /**
     * Get videos related to each channel with set of fields
     */
    override suspend fun getChannelVideos(
        channel: String,
        page: Int,
        limit: Int,
        fields: String
    ): VideoDto {
        return api.getChannelVideos(channel, page, limit, fields)
    }
}