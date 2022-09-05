package com.choota.dmotion.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.choota.dmotion.data.local.DMotionDatabase
import com.choota.dmotion.data.remote.DMotionAPI
import com.choota.dmotion.data.remote.PixabayAPI
import com.choota.dmotion.data.repository.DMotionRepositoryImpl
import com.choota.dmotion.data.repository.PixabayRepositoryImpl
import com.choota.dmotion.data.repository.local.ResourceVideoRepositoryImpl
import com.choota.dmotion.domain.repository.DMotionRepository
import com.choota.dmotion.domain.repository.PixabayRepository
import com.choota.dmotion.domain.repository.local.ResourceVideoRepository
import com.choota.dmotion.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeAppModule {

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
     * Pixabay retrofit instance for the application
     */
    @Provides
    @Singleton
    fun providesPixabayAPI(): PixabayAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.PixaBASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayAPI::class.java)
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

    /**
     * PixabayRepository that will create an instance of impl of the repo.
     * Using this to call the APIs
     */
    @Provides
    @Singleton
    fun providesPixabayRepository(api: PixabayAPI): PixabayRepository {
        return PixabayRepositoryImpl(api)
    }

    /**
     * PixabayRepository that will create an instance of impl of the repo.
     * Using this to call the APIs
     */
    @Provides
    @Singleton
    fun providesImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }

    /**
     * Provides db for the application
     */
    @Provides
    @Singleton
    fun providesFakeDMotionDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, DMotionDatabase::class.java
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    /**
     * Provides ResourceVideo repo for access
     */
    @Provides
    @Singleton
    fun providesFakeResourceVideoRepo(db: DMotionDatabase): ResourceVideoRepository {
        return ResourceVideoRepositoryImpl(db.resourceVideoDao)
    }
}