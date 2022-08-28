package com.choota.dmotion.domain.use_case.get_channels

import com.choota.dmotion.data.remote.dto.toPage
import com.choota.dmotion.domain.model.ChannelPage
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

/**
 * GetChannelsUseCase is used to get all cahnnels
 * Can use for pagination
 * Have used kotlin flow and will emit results from time to time
 */
class GetChannelsUseCase @Inject constructor(private val repository: DMotionRepository) {

    /**
     * Used kotlin operator function, so it's corresponding member function is called automatically
     *
     * @param page pagination requested page
     */
    operator fun invoke(page: Int): Flow<Resource<ChannelPage>> = flow {
        try {
            emit(Resource.Loading<ChannelPage>())
            val page = repository.getChannels(page).toPage()
            emit(Resource.Success<ChannelPage>(page))
        } catch (e: HttpException) {
            emit(
                Resource.Error<ChannelPage>(
                    e.localizedMessage ?: "There is an exception occurred on HTTP Connection"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<ChannelPage>(
                    e.localizedMessage
                        ?: "Couldn't connect to server. Please check the network connection"
                )
            )
        } catch (e: SocketException) {
            emit(
                Resource.Error<ChannelPage>(
                    e.localizedMessage ?: "There is an exception occurred on Socket Connection"
                )
            )
        }
    }
}