package com.ccs.chartai.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chart_history")
data class ChartHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val query: String,
    // Add other fields relevant to your chat history, e.g., response, image_uri
)