package com.choota.dmotion.domain.use_case.get_channel_videos

import com.choota.dmotion.data.remote.dto.toPage
import com.choota.dmotion.domain.model.ChannelPage
import com.choota.dmotion.domain.model.VideoPage
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

/**
 * GetChannelVideosUseCase is used to get popular movies
 * Can use for pagination
 * Have used kotlin flow and will emit results from time to time
 */
class GetChannelVideosUseCase @Inject constructor(private val repository: DMotionRepository) {

    /**
     * Used kotlin operator function, so it's corresponding member function is called automatically
     *
     * @param channel change channel
     * @param page pagination requested page
     * @param limit pagination limit change
     * @param fields change list of fields needed
     */
    operator fun invoke(
        channel: String,
        page: Int,
        limit: Int,
        fields: String
    ): Flow<Resource<VideoPage>> = flow {
        try {
            emit(Resource.Loading<VideoPage>())
            val page = repository.getChannelVideos(channel, page, limit, fields).toPage()
            emit(Resource.Success<VideoPage>(page))
        } catch (e: HttpException) {
            emit(
                Resource.Error<VideoPage>(
                    e.localizedMessage ?: "There is an exception occurred on HTTP Connection"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<VideoPage>(
                    e.localizedMessage
                        ?: "Couldn't connect to server. Please check the network connection"
                )
            )
        } catch (e: SocketException) {
            emit(
                Resource.Error<VideoPage>(
                    e.localizedMessage ?: "There is an exception occurred on Socket Connection"
                )
            )
        }
    }
}