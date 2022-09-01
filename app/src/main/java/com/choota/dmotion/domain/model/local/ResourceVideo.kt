package com.choota.dmotion.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResourceVideo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var link: String,
    var playTime: Long = 0
)
