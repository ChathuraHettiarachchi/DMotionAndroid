package com.choota.dmotion.data.remote

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.PixabayDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Dailymotion API list for DMotion application
 */
interface PixabayAPI {
    @GET("/api")
    suspend fun getPictures(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("image_type") type: String = "photo",
        @Query("key") key: String = Constants.PixabayKey
    ): PixabayDto
}