package com.ccs.chartai.di

import android.content.Context
import com.ccs.chartai.network.ChartApiService
import com.ccs.chartai.repository.LocalRepository
import com.ccs.chartai.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideChartApiService(retrofit: Retrofit): ChartApiService =
        retrofit.create(ChartApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteRepository(api: ChartApiService) = RemoteRepository(api, Dispatchers.IO)

    @Singleton
    @Provides
    fun provideLocalRepository(@ApplicationContext context: Context) = LocalRepository(context)
}