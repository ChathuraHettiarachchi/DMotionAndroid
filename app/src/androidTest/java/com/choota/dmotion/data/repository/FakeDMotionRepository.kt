package com.choota.dmotion.data.repository

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.util.Constants
import com.google.gson.Gson
import java.net.SocketException

class FakeDMotionRepository : DMotionRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getChannels(page: Int): ChannelDto {
        if (shouldReturnNetworkError) {
            throw SocketException("Unable to get the data object from server")
        } else {
            return Gson().fromJson(Constants.channels, ChannelDto::class.java)
        }

    }

    override suspend fun getChannelVideos(
        channel: String,
        page: Int,
        limit: Int,
        fields: String
    ): VideoDto {
        if (shouldReturnNetworkError) {
            throw SocketException("Unable to get the data object from server")
        } else {
            return Gson().fromJson(Constants.videos, VideoDto::class.java)
        }
    }

}