package com.choota.dmotion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.choota.dmotion.data.local.dao.ResourceVideoDao
import com.choota.dmotion.domain.model.local.ResourceVideo

@Database(
    entities = [ResourceVideo::class],
    version = 1,
    exportSchema = false
)
abstract class DMotionDatabase: RoomDatabase() {
    abstract val resourceVideoDao: ResourceVideoDao
}