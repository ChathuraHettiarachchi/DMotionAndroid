package com.choota.dmotion.domain.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ResourceVideo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var link: String,
    var playTime: Long = 0
): Parcelable
