package com.choota.dmotion.di

import android.provider.SyncStateContract
import com.choota.dmotion.data.remote.DMotionAPI
import com.choota.dmotion.data.repository.DMotionRepositoryImpl
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.util.Constants
import com.choota.dmotion.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * DI module fo the application
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * OkHttpClient from connection timeouts and logs
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Retrofit instance for the application
     */
    @Provides
    @Singleton
    fun providesDMotionAPI(client: OkHttpClient): DMotionAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DMotionAPI::class.java)
    }

    /**
     * DMotionRepository that will create an instance of impl of the repo.
     * Using this to call the APIs
     */
    @Provides
    @Singleton
    fun providesDMotionRepository(api: DMotionAPI): DMotionRepository {
        return DMotionRepositoryImpl(api)
    }
}