package com.duycomp.autoclicker.network.retrofit.di

import com.duycomp.autoclicker.network.retrofit.TimeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.timeapi.io/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideTimeService(retrofit: Retrofit): TimeService =
        retrofit.create(TimeService::class.java)
}