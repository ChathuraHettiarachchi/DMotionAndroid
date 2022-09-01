package com.choota.dmotion.data.local.dao

import androidx.room.*
import com.choota.dmotion.domain.model.local.ResourceVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceVideoDao {

    @Query("SELECT * FROM ResourceVideo")
    fun getVideos(): Flow<List<ResourceVideo>>

    @Query("SELECT * FROM ResourceVideo WHERE id = :id")
    suspend fun getVideoById(id: Long): ResourceVideo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: ResourceVideo): Long

    @Update
    suspend fun updateVideo(video: ResourceVideo)

    @Delete
    suspend fun deleteVideo(video: ResourceVideo)

    @Query("SELECT id, link, min(playTime) AS 'playTime' FROM ResourceVideo")
    suspend fun getLeastPlayedVideo(): ResourceVideo
}