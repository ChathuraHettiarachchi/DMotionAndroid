package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import javax.inject.Inject

class GetLeastPlayedVideoUseCase @Inject constructor(
    private val repository: ResourceVideoRepository
) {
    suspend operator fun invoke(): ResourceVideo {
        return repository.getLeastPlayedVideo()
    }
}