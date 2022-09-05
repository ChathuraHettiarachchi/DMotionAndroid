package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import androidx.test.filters.MediumTest
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
class GetLeastPlayedVideoUseCaseTest {
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
    fun verify_that_least_played_video_will_return_from_db() = runBlocking {
        val videos = listOf<ResourceVideo>(
            ResourceVideo(0, "Link 01", 10L),
            ResourceVideo(0, "Link 02", 5L),
            ResourceVideo(0, "Link 03", 1L),
            ResourceVideo(0, "Link 04", 15L),
            ResourceVideo(0, "Link 05", 55L),
        )

        videos.forEach {
            repository.insertVideo(
                it
            )
        }
        
        val leastPlayedVideo = repository.getLeastPlayedVideo()
        assertThat(leastPlayedVideo.link.isNotEmpty()).isTrue()
        assertThat(leastPlayedVideo.link == "Link 03").isTrue()
        assertThat(leastPlayedVideo.playTime == 1L).isTrue()
    }

    @After
    fun closeDb() {
        db.close()
    }
}