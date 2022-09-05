package com.choota.dmotion.domain.use_case.get_channel_videos

import app.cash.turbine.test
import com.choota.dmotion.data.repository.FakeDMotionRepository
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetChannelVideosUseCaseTest{
    private lateinit var useCaseChannelVideoError: GetChannelVideosUseCase
    private lateinit var useCaseChannelVideoSuccess: GetChannelVideosUseCase

    val channel =  "Car"
    val page = 1
    val limit = 50
    val fields = Constants.VIDEO_FIELDS

    @Before
    fun setup(){
        useCaseChannelVideoError = GetChannelVideosUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(true) })
        useCaseChannelVideoSuccess = GetChannelVideosUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(false) })
    }

    // Channel useCase testing
    @Test
    fun `verify Channel state change from loading to error on API or any error`() = runBlocking{
        useCaseChannelVideoError.invoke(channel, page, limit, fields).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitError = awaitItem()
            assertThat(emitError).isInstanceOf(Resource.Error::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify Channel state change from loading to success on API success`() = runBlocking{
        useCaseChannelVideoSuccess.invoke(channel, page, limit, fields).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify Channel API success return Channel details`() = runBlocking{
        useCaseChannelVideoSuccess.invoke(channel, page, limit, fields).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            assertThat(emitSuccess.data!!.list.size>1).isTrue()
            assertThat(emitSuccess.data!!.list[1].channelName == "Cars").isTrue()

            awaitComplete()
        }
    }
}