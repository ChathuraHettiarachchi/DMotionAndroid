package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@UninstallModules(AppModule::class)
@HiltAndroidTest
class GetVideoByIdUseCaseTest{
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
    fun check_video_by_id() = runBlocking {
        val video = ResourceVideo(0, "Link")
        var id = -1L
        var videoFromDB = ResourceVideo(0, "")

        id = repository.insertVideo(
            video
        )

        videoFromDB = repository.getVideoById(id)
        assertThat(id >= 0).isTrue()
        assertThat(videoFromDB.link.isNotEmpty()).isTrue()
        assertThat(videoFromDB.link == "Link").isTrue()
    }

    @After
    fun closeDb(){
        db.close()
    }
}