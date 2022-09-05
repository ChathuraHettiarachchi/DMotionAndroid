package com.choota.dmotion.domain.use_case.local.use_case_resource_video


import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@UninstallModules(AppModule::class)
@HiltAndroidTest
class GetVideosUseCaseTest {

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
    fun check_getAll_after_data_insert() = runBlocking {
        Constants.RESOURCE_VIDEOS.forEach {
            repository.insertVideo(
                ResourceVideo(0, it)
            )
        }

        repository.getVideos().test {
            val emitList = awaitItem()
            assertThat(emitList).isNotEmpty()
            assertThat(emitList.size == Constants.RESOURCE_VIDEOS.size).isTrue()
        }
    }

    @After
    fun closeDb(){
        db.close()
    }
}