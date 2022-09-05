package com.choota.dmotion.presentation.videos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.choota.dmotion.MainCoroutineRule
import com.choota.dmotion.data.repository.FakeDMotionRepository
import com.choota.dmotion.domain.use_case.get_channel_videos.GetChannelVideosUseCase
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class VideoViewModelTest {
    private lateinit var viewModelError: VideoViewModel
    private lateinit var viewModelSuccess: VideoViewModel

    private lateinit var useCaseError: GetChannelVideosUseCase
    private lateinit var useCaseSuccess: GetChannelVideosUseCase

    val channel = "Car"
    val page = 1
    val limit = 50
    val fields = Constants.VIDEO_FIELDS

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        useCaseSuccess = GetChannelVideosUseCase(FakeDMotionRepository().apply {
            setShouldReturnNetworkError(false)
        })
        useCaseError =
            GetChannelVideosUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(true) })

        viewModelError = VideoViewModel(
            useCaseError
        )
        viewModelSuccess = VideoViewModel(
            useCaseSuccess
        )

        viewModelError.getVideos(channel, page, limit)
        viewModelSuccess.getVideos(channel, page, limit)
    }

    @Test
    fun `error on getVideos isTrue`() {
        val result = viewModelError.videoState.value
        Truth.assertThat(result.error.isNotBlank()).isTrue()
    }

    @Test
    fun `success on getVideos isTrue`() = runBlocking {
        useCaseSuccess.invoke(channel, page, limit, fields).test {
            val emitLoading = awaitItem()
            Truth.assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            Truth.assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            Truth.assertThat(!viewModelSuccess.videoState.value.isLoading).isTrue()

            awaitComplete()

            Truth.assertThat(viewModelSuccess.videoState.value.data.list.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun `success on getVideos and vide is attached with data`() = runBlocking {
        useCaseSuccess.invoke(channel, page, limit, fields).test {
            val emitLoading = awaitItem()
            Truth.assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            Truth.assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            Truth.assertThat(!viewModelSuccess.videoState.value.isLoading).isTrue()

            awaitComplete()

            Truth.assertThat(viewModelSuccess.videoState.value.data.list.isNotEmpty()).isTrue()
            Truth.assertThat(viewModelSuccess.videoState.value.data.list[0].title!!.isNotEmpty())
                .isTrue()
            Truth.assertThat(viewModelSuccess.videoState.value.data.list[0].description!!.isNotEmpty())
                .isTrue()
            Truth.assertThat(viewModelSuccess.videoState.value.data.list[0].duration!! > 0).isTrue()
            Truth.assertThat(viewModelSuccess.videoState.value.data.list[0].language!!.isNotEmpty())
                .isTrue()
            Truth.assertThat(viewModelSuccess.videoState.value.data.list[0].thumbnail720Url!!.isNotEmpty())
                .isTrue()
        }
    }
}