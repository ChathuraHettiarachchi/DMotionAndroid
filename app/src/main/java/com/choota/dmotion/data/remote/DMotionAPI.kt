package com.choota.dmotion.data.remote

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Dailymotion API list for DMotion application
 */
interface DMotionAPI {
    @GET("/channels")
    suspend fun getChannels(): ChannelDto

    @GET("/channel/{channel}/videos?fields={fields}&page={page}&limit={limit}")
    suspend fun getChannelVideos(
        @Path("channel") channel: String,
        @Path("page") page: Int,
        @Path("limit") limit: Int,
        @Path("fields") fields: String = Constants.VIDEO_FIELDS
    ): VideoDto
}