package com.choota.dmotion.domain.repository.local

import com.choota.dmotion.domain.model.local.ResourceVideo
import kotlinx.coroutines.flow.Flow

interface ResourceVideoRepository {
    fun getVideos(): Flow<List<ResourceVideo>>
    suspend fun getVideoById(id: Long): ResourceVideo
    suspend fun insertVideo(video: ResourceVideo): Long
    suspend fun updateVideo(video: ResourceVideo)
    suspend fun deleteVideo(video: ResourceVideo)
    suspend fun getLeastPlayedVideo(): ResourceVideo
}