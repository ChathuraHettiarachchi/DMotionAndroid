package com.choota.dmotion.data.repository.local

import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

class FakeResourceVideoRepository : ResourceVideoRepository {
    override fun getVideos(): Flow<List<ResourceVideo>> {
        val list: MutableList<ResourceVideo> = mutableListOf()
        Constants.RESOURCE_VIDEOS.forEach { link ->
            list.add(ResourceVideo(0, link))
        }

        return flow{list.toList()}
    }

    override suspend fun getVideoById(id: Long): ResourceVideo {
        return ResourceVideo(0, Constants.RESOURCE_VIDEOS[0], 0)
    }

    override suspend fun insertVideo(video: ResourceVideo): Long {
        return 0
    }

    override suspend fun updateVideo(video: ResourceVideo) {}

    override suspend fun deleteVideo(video: ResourceVideo) {}

    override suspend fun getLeastPlayedVideo(): ResourceVideo {
        return ResourceVideo(0, Constants.RESOURCE_VIDEOS[0], 0)
    }
}