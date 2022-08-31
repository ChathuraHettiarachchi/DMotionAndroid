package com.choota.dmotion.data.repository

import com.choota.dmotion.data.remote.DMotionAPI
import com.choota.dmotion.data.remote.PixabayAPI
import com.choota.dmotion.data.remote.dto.ChannelDto
import com.choota.dmotion.data.remote.dto.PixabayDto
import com.choota.dmotion.data.remote.dto.VideoDto
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.domain.repository.PixabayRepository
import com.choota.dmotion.util.Constants
import javax.inject.Inject

/**
 * Pixabay implementation from domain repository. This implements the actions defined in the
 * repo of domain package.
 * Reason is to support great scalability.
 */
class PixabayRepositoryImpl @Inject constructor(private val api: PixabayAPI) : PixabayRepository {

    /**
     * Get images
     */
    override suspend fun getImages(
        query: String,
        category: String,
        type: String,
        key: String
    ): PixabayDto {
        return api.getPictures(query, category)
    }
}