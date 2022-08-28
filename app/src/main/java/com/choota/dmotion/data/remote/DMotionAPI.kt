package com.choota.dmotion.data.remote

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.VideoDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Dailymotion API list for DMotion application
 */
interface DMotionAPI {
    @GET("/channels")
    suspend fun getChannels(): ChannelDto

    @GET("/channel/{channel}/videos?fields=views_total,audience,channel.name,country,duration,thumbnail_720_url,title,tags,description,language&page={page}&limit={limit}")
    suspend fun getChannelVideos(
        @Path("channel") movieId: String,
        @Path("page") page: Int,
        @Path("limit") limit: Int,
    ): VideoDto
}