package com.fuze.csgo.di

import com.fuze.csgo.api.ApiFuzeService
import com.fuze.csgo.api.ApiInterceptor
import com.fuze.csgo.other.Constants.BASE_URL
import com.fuze.csgo.repository.DefaultFuzeRepository
import com.fuze.csgo.repository.FuzeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor())
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun providesDefaultFuzeRepository(
        api: ApiFuzeService
    ) = DefaultFuzeRepository(api) as FuzeRepository

    @Singleton
    @Provides
    fun providesApiFuzeService(): ApiFuzeService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(provideInterceptor())
            .build()
            .create(ApiFuzeService::class.java)
    }
}