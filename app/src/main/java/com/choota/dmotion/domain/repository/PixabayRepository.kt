package com.choota.dmotion.domain.repository

import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.PixabayDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.util.Constants

interface PixabayRepository {
    suspend fun getImages(query: String,
                          category: String,
                          type: String = "photo",
                          key: String = Constants.PixabayKey): PixabayDto
}