package com.ccs.chartai.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChartHistoryItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chartHistoryDao(): ChartHistoryDao
}