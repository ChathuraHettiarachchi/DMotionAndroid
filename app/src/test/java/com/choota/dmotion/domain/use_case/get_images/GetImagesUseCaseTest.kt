package com.choota.dmotion.domain.use_case.get_images

import app.cash.turbine.test
import com.choota.dmotion.data.repository.FakePixabayRepository
import com.choota.dmotion.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetImagesUseCaseTest{
    private lateinit var useCaseImageError: GetImagesUseCase
    private lateinit var useCaseImageSuccess: GetImagesUseCase

    val type =  "auto"
    val category =  "auto"

    @Before
    fun setup(){
        useCaseImageError = GetImagesUseCase(FakePixabayRepository().apply { setShouldReturnNetworkError(true) })
        useCaseImageSuccess = GetImagesUseCase(FakePixabayRepository().apply { setShouldReturnNetworkError(false) })
    }

    // Image useCase testing
    @Test
    fun `verify Image state change from loading to error on API or any error`() = runBlocking{
        useCaseImageError.invoke(type, category).test {
            val emitLoading = awaitItem()
            Truth.assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitError = awaitItem()
            Truth.assertThat(emitError).isInstanceOf(Resource.Error::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify Image state change from loading to success on API success`() = runBlocking{
        useCaseImageSuccess.invoke(type, category).test {
            val emitLoading = awaitItem()
            Truth.assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            Truth.assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            awaitComplete()
        }
    }

    @Test
    fun `verify Image API success return Image details`() = runBlocking{
        useCaseImageSuccess.invoke(type, category).test {
            val emitLoading = awaitItem()
            Truth.assertThat(emitLoading).isInstanceOf(Resource.Loading::class.java)

            val emitSuccess = awaitItem()
            Truth.assertThat(emitSuccess).isInstanceOf(Resource.Success::class.java)

            Truth.assertThat(emitSuccess.data!!.list.size>1).isTrue()
            Truth.assertThat(emitSuccess.data!!.list[0].webformatURL == "https://pixabay.com/get/ge969c0091371b7aabecf397e81d8fef4de11022ea61a323084373fd5c960fc897639147e2cd705cf14b5de630861b8f6_640.jpg").isTrue()

            awaitComplete()
        }
    }
}