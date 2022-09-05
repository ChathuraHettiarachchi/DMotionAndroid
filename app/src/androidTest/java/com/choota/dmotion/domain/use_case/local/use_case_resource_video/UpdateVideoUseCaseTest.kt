package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import app.cash.turbine.test
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AppModule::class)
@HiltAndroidTest
class UpdateVideoUseCaseTest {
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
    fun check_db_data_update_after_insert() = runBlocking {
        val video = ResourceVideo(0, "Link")
        var videoFromDB: ResourceVideo? = null
        repository.insertVideo(
            video
        )

        repository.getVideos().test {
            val emitList = awaitItem()
            assertThat(emitList).isNotEmpty()
            assertThat(emitList[0].link == "Link").isTrue()
            videoFromDB = emitList[0]
        }

        repository.updateVideo(
            videoFromDB!!.apply {
                link = "updated"
            }
        )

        repository.getVideos().test {
            val emitList = awaitItem()
            assertThat(emitList).isNotEmpty()
            assertThat(emitList[0].link == "updated").isTrue()
        }
    }

    @After
    fun closeDb() {
        db.close()
    }
}