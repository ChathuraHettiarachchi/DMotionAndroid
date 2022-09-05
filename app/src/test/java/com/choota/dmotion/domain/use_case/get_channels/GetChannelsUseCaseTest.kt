package com.choota.dmotion.domain.use_case.get_channels

import app.cash.turbine.test
import com.choota.dmotion.data.repository.FakeDMotionRepository
import com.choota.dmotion.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetChannelsUseCaseTest{

    private lateinit var useCaseError: GetChannelsUseCase
    private lateinit var useCaseSuccess: GetChannelsUseCase

    private val id = 1

    @Before
    fun setup(){
        useCaseError = GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(true) })
        useCaseSuccess = GetChannelsUseCase(FakeDMotionRepository().apply { setShouldReturnNetworkError(false) })
    }

    @Test
    fun `verify state change from loading to error on API or any error`() = runBlocking{
        useCaseError.invoke(id).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitError = awaitItem()
            assertThat(emitError).isInstanceOf(Resource.Error::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify state change from loading to success on API success`() = runBlocking{
        useCaseSuccess.invoke(id).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify API success return movie details`() = runBlocking{
        useCaseSuccess.invoke(id).test {
            val emitLoading = awaitItem()
            assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            assertThat(emitSuccess.data!!.page == id).isTrue()

            awaitComplete()
        }
    }
}