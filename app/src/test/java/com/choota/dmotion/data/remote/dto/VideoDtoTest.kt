package com.choota.dmotion.data.remote.dto

import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class VideoDtoTest{
    @Test
    fun `verify VideoDto can generate video page isTrue`(){
        val data = Gson().fromJson(Constants.videos, VideoDto::class.java)
        Truth.assertThat(data != null).isTrue()
        Truth.assertThat(data.toPage().page == 1).isTrue()
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
    }

    @Test
    fun `verify VideoDto can generate video list isTrue`(){
        val data = Gson().fromJson(Constants.videos, VideoDto::class.java)
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
        Truth.assertThat(data.toPage().list.size == 10).isTrue()
    }

    @Test
    fun `verify VideoDto contains correct data from format`(){
        val data = Gson().fromJson(Constants.videos, VideoDto::class.java)
        Truth.assertThat(data.list.isNotEmpty()).isTrue()
        Truth.assertThat(data.list.first().title != null).isTrue()
        Truth.assertThat(data.list.first().channelName == "Cars").isTrue()
    }
}