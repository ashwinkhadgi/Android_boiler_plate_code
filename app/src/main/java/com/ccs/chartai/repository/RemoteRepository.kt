package com.ccs.chartai.repository


import com.ccs.chartai.data.ChatResponse
import com.ccs.chartai.network.ChartApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteRepository(
    private val api: ChartApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun analyzeImage(imageBytes: ByteArray): Flow<ChatResponse> = flow {
      //  val response = api.analyzeImage(imageBytes)
        emit(ChatResponse())
    }.flowOn(ioDispatcher)
}