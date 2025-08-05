package com.ccs.chartai.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(item: ChartHistoryItem)

    @Query("SELECT * FROM chart_history ORDER BY timestamp DESC")
    fun getAllHistoryItems(): Flow<List<ChartHistoryItem>>

    @Query("DELETE FROM chart_history WHERE id = :itemId")
    suspend fun deleteHistoryItem(itemId: Long) // Or Int

    @Query("DELETE FROM chart_history")
    suspend fun clearAllHistory() // O
}