package com.choota.dmotion.data.remote.dto

import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class PixabayDtoTest{
    @Test
    fun `verify PixabayDto can generate image page isTrue`(){
        val data = Gson().fromJson(Constants.images, PixabayDto::class.java)
        Truth.assertThat(data != null).isTrue()
    }

    @Test
    fun `verify PixabayDto can generate image list isTrue`(){
        val data = Gson().fromJson(Constants.images, PixabayDto::class.java)
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
        Truth.assertThat(data.toPage().list.size == 20).isTrue()
    }

    @Test
    fun `verify PixabayDto contains correct data from format`(){
        val data = Gson().fromJson(Constants.images, PixabayDto::class.java)
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
        Truth.assertThat(data.toPage().list.first().webformatURL != null).isTrue()
        Truth.assertThat(data.toPage().list.first().webformatURL == "https://pixabay.com/get/ge969c0091371b7aabecf397e81d8fef4de11022ea61a323084373fd5c960fc897639147e2cd705cf14b5de630861b8f6_640.jpg").isTrue()
    }
}