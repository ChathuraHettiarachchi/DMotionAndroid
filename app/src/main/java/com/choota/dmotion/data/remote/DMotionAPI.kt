package com.choota.dmotion.data.remote

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Dailymotion API list for DMotion application
 */
interface DMotionAPI {
    @GET("/channels")
    suspend fun getChannels(@Query("page") page: Int): ChannelDto

    @GET("/channel/{channel}/videos")
    suspend fun getChannelVideos(
        @Query("channel") channel: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("fields") fields: String = Constants.VIDEO_FIELDS
    ): VideoDto
}