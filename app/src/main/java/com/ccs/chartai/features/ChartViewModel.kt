package com.ccs.chartai.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccs.chartai.data.ChatResponse
import com.ccs.chartai.db.ChartHistoryDao
import com.ccs.chartai.db.ChartHistoryItem
import com.ccs.chartai.repository.LocalRepository
import com.ccs.chartai.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val localRepo: LocalRepository, // We'll keep this for DataStore for now
    private val chartHistoryDao: ChartHistoryDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatResponse?>(null)
    val uiState: StateFlow<ChatResponse?> = _uiState

    // Example: Flow to hold chart history from Room
    val chartHistory = chartHistoryDao.getAllHistoryItems()

    fun analyze(imageBytes: ByteArray, queryText: String) { // Added queryText for history
        viewModelScope.launch {
            remoteRepo.analyzeImage(imageBytes).collectLatest { response ->
                _uiState.value = response
                if (response != null) {
                    // Save to DataStore (as per previous setup)
                    localRepo.saveResponse(response)

                    // Save to Room Database
                    val historyItem = ChartHistoryItem(query = queryText)
                    chartHistoryDao.insertHistoryItem(historyItem)
                }
            }
        }
    }

    // Example function to clear a specific history item
    fun clearHistoryItem(itemId: Long) {
        viewModelScope.launch {
            chartHistoryDao.deleteHistoryItem(itemId)
        }
    }

    // Example function to clear all history
    fun clearAllHistory() {
        viewModelScope.launch {
            chartHistoryDao.clearAllHistory()
        }
    }
}