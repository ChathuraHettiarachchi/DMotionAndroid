package com.choota.dmotion.data.repository

import com.choota.dmotion.data.remote.dto.PixabayDto
import com.choota.dmotion.domain.repository.PixabayRepository
import com.choota.dmotion.util.Constants
import com.google.gson.Gson
import java.net.SocketException

class FakePixabayRepository : PixabayRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getImages(
        query: String,
        category: String,
        type: String,
        key: String
    ): PixabayDto {
        if (shouldReturnNetworkError) {
            throw SocketException("Unable to get the data object from server")
        } else {
            return Gson().fromJson(Constants.images, PixabayDto::class.java)
        }
    }
}