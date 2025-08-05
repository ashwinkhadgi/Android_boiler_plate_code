package com.ccs.chartai.di

import android.content.Context
import androidx.room.Room
import com.ccs.chartai.db.AppDatabase
import com.ccs.chartai.db.ChartHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "chart_ai_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChartHistoryDao(appDatabase: AppDatabase): ChartHistoryDao {
        return appDatabase.chartHistoryDao()
    }
}