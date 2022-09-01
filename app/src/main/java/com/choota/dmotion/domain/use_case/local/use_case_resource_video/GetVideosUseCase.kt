package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: ResourceVideoRepository
) {
    operator fun invoke(): Flow<List<ResourceVideo>> {
        return repository.getVideos()
    }
}