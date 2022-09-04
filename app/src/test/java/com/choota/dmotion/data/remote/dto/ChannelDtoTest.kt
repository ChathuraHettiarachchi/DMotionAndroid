package com.choota.dmotion.data.remote.dto

import com.choota.dmotion.util.Constants
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Test

class ChannelDtoTest{
    @Test
    fun `verify ChannelDto can generate channel page isTrue`(){
        val data = Gson().fromJson(Constants.channels, ChannelDto::class.java)
        Truth.assertThat(data != null).isTrue()
        Truth.assertThat(data.toPage().page == 1).isTrue()
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
    }

    @Test
    fun `verify ChannelDto can generate channel list isTrue`(){
        val data = Gson().fromJson(Constants.channels, ChannelDto::class.java)
        Truth.assertThat(data.toPage().list.isNotEmpty()).isTrue()
        Truth.assertThat(data.toPage().list.size == 17).isTrue()
    }

    @Test
    fun `verify ChannelDto contains correct data from format`(){
        val data = Gson().fromJson(Constants.channels, ChannelDto::class.java)
        Truth.assertThat(data.list.isNotEmpty()).isTrue()
        Truth.assertThat(data.list.first().name != null).isTrue()
        Truth.assertThat(data.list.first().id == "animals").isTrue()
        Truth.assertThat(data.list.first().name == "Animals").isTrue()
    }
}