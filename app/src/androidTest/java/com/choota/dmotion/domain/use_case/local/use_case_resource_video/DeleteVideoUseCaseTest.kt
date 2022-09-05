package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@UninstallModules(AppModule::class)
@HiltAndroidTest
class DeleteVideoUseCaseTest{
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: DMotionDatabase

    @Inject
    lateinit var repository: ResourceVideoRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_db_data_delete_after_insert() = runBlocking {
        var videosFromDb: List<ResourceVideo> = listOf()
        Constants.RESOURCE_VIDEOS.forEach {
            repository.insertVideo(
                ResourceVideo(0, it)
            )
        }

        repository.getVideos().test {
            val emitList = awaitItem()
            Truth.assertThat(emitList).isNotEmpty()
            Truth.assertThat(emitList.size == Constants.RESOURCE_VIDEOS.size).isTrue()
            videosFromDb = emitList
        }

        repository.deleteVideo(videosFromDb[0])

        repository.getVideos().test {
            val emitList = awaitItem()
            Truth.assertThat(emitList).isNotEmpty()
            Truth.assertThat(emitList.size != Constants.RESOURCE_VIDEOS.size).isTrue()
            Truth.assertThat(emitList.size == Constants.RESOURCE_VIDEOS.size - 1).isTrue()
        }
    }

    @After
    fun closeDb(){
        db.close()
    }
}