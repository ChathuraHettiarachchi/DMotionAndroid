package com.choota.dmotion.domain.use_case.local.use_case_resource_video

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.choota.dmotion.HiltTestRunner
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.data.local.dao.ResourceVideoDao
import com.choota.dmotion.data.repository.local.ResourceVideoRepositoryImpl
import com.choota.dmotion.di.AppModule
import com.choota.dmotion.domain.model.local.ResourceVideo
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth.assertThat
import dagger.Binds
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@UninstallModules(AppModule::class)
@HiltAndroidTest
class GetVideosUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

//    @Inject
//    @Named("fake-db")
//    lateinit var db: DMotionDatabase

//    @Inject
//    @Named("fake-repo")
//    lateinit var repository: ResourceVideoRepository

//    lateinit var dao: ResourceVideoDao

    @Before
    fun init() {
        hiltRule.inject()
//        dao = db.resourceVideoDao
    }

    @Test
    fun check_getAll_after_data_insert() = runBlocking {
//        Constants.RESOURCE_VIDEOS.forEach {
//            dao.insertVideo(
//                ResourceVideo(0, it)
//            )
//        }
//
//        dao.getVideos().collectLatest {
//            assertThat(it.isNotEmpty()).isTrue()
//        }
    }
//
//    @After
//    fun closeDb(){
//        db.close()
//    }
}