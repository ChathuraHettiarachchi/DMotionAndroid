package com.choota.dmotion.data.repository.local

import com.choota.dmotion.data.local.dao.ResourceVideoDao
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResourceVideoRepositoryImpl @Inject constructor(private val dao: ResourceVideoDao) :
    ResourceVideoRepository {
    override fun getVideos(): Flow<List<ResourceVideo>> {
        return dao.getVideos()
    }

    override suspend fun getVideoById(id: Long): ResourceVideo {
        return dao.getVideoById(id)
    }

    override suspend fun insertVideo(video: ResourceVideo): Long {
        return dao.insertVideo(video)
    }

    override suspend fun updateVideo(video: ResourceVideo) {
        return dao.updateVideo(video)
    }

    override suspend fun deleteVideo(video: ResourceVideo) {
        return dao.deleteVideo(video)
    }

    override suspend fun getLeastPlayedVideo(): ResourceVideo {
        return dao.getLeastPlayedVideo()
    }
}