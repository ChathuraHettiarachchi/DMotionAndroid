package com.choota.dmotion.presentation.channels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.choota.dmotion.MainCoroutineRule
import com.choota.dmotion.data.repository.FakeDMotionRepository
import com.choota.dmotion.data.repository.FakePixabayRepository
import com.choota.dmotion.data.repository.local.FakeResourceVideoRepository
import com.choota.dmotion.domain.use_case.get_channels.GetChannelsUseCase
import com.choota.dmotion.domain.use_case.get_images.GetImagesUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.GetVideosUseCase
import com.choota.dmotion.domain.use_case.local.use_case_resource_video.InsertVideoUseCase
import com.choota.dmotion.util.Resource
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChannelViewModelTest {

    private lateinit var viewModelError: ChannelViewModel
    private lateinit var viewModelSuccess: ChannelViewModel

    private lateinit var useCaseError: GetChannelsUseCase
    private lateinit var useCaseSuccess: GetChannelsUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        useCaseSuccess = GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(false) })
        useCaseError = GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(true) })

        viewModelError = ChannelViewModel(
            GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(true) }),
            GetImagesUseCase(
                FakePixabayRepository()
            ),
            GetVideosUseCase(FakeResourceVideoRepository()),
            InsertVideoUseCase(FakeResourceVideoRepository())
        )
        viewModelSuccess = ChannelViewModel(
            GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(false) }),
            GetImagesUseCase(
                FakePixabayRepository()
            ),
            GetVideosUseCase(FakeResourceVideoRepository()),
            InsertVideoUseCase(FakeResourceVideoRepository())
        )
    }

    @Test
    fun `error on getChannels isTrue`() {
        val result = viewModelError.channelState.value
        assertThat(result.error.isNotBlank()).isTrue()
    }

    @Test
    fun `success on getChannels isTrue`() = runBlocking {
        useCaseSuccess.invoke(1).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)
            assertThat(!viewModelSuccess.channelState.value.isLoading).isTrue()

            awaitComplete()

            assertThat(viewModelSuccess.channelState.value.data.list.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun `success on getChannels and channel is attached with image`() = runBlocking{
        useCaseSuccess.invoke(1).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)
            assertThat(!viewModelSuccess.channelState.value.isLoading).isTrue()

            awaitComplete()

            assertThat(viewModelSuccess.channelState.value.data.list.isNotEmpty()).isTrue()
            assertThat(viewModelSuccess.channelState.value.data.list[0].image.isNotEmpty()).isTrue()
        }
    }
}