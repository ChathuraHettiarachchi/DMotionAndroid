package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import androidx.room.Insert
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import javax.inject.Inject

class InsertVideoUseCase @Inject constructor(
    private val repository: ResourceVideoRepository
) {
    suspend operator fun invoke(video: ResourceVideo): Long{
        return repository.insertVideo(video)
    }
}