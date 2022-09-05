package com.choota.dmotion.domain.use_case.get_images

import com.choota.dmotion.data.remote.dto.toPage
import com.choota.dmotion.domain.model.PixImagePage
import com.choota.dmotion.domain.repository.PixabayRepository
import com.choota.dmotion.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: PixabayRepository) {

    operator fun invoke(query: String, category: String): Flow<Resource<PixImagePage>> = flow {
        try {
            emit(Resource.Loading<PixImagePage>())
            val page = repository.getImages(query, category).toPage()
            emit(Resource.Success<PixImagePage>(page))
        } catch (e: HttpException) {
            emit(
                Resource.Error<PixImagePage>(
                    e.localizedMessage ?: "There is an exception occurred on HTTP Connection"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<PixImagePage>(
                    e.localizedMessage
                        ?: "Couldn't connect to server. Please check the network connection"
                )
            )
        } catch (e: SocketException) {
            emit(
                Resource.Error<PixImagePage>(
                    e.localizedMessage ?: "There is an exception occurred on Socket Connection"
                )
            )
        }
    }
}