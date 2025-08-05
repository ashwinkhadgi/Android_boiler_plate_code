package com.ccs.chartai.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ccs.chartai.data.ChatResponse // Assuming this is the correct import
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Extension property to create the DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LocalRepository @Inject constructor(private val context: Context) {

    // Define a key for storing the chat response
    private val CHAT_RESPONSE_KEY = stringPreferencesKey("chat_response_data") // Renamed key for clarity

    // Function to save the ChatResponse to DataStore
    suspend fun saveResponse(response: ChatResponse) { // Renamed function, changed parameter type
        context.dataStore.edit { preferences ->
            // Storing the response as a string. Consider JSON serialization for complex objects.
            preferences[CHAT_RESPONSE_KEY] = response.toString() 
        }
    }

    // Function to read the chat response from DataStore
    // Assuming you want to reconstruct ChatResponse from String. 
    // This will need proper deserialization if you stored a complex object.
    // For now, it reads as a String. You might need to adjust this part.
    val readResponse: Flow<String?> = context.dataStore.data 
        .map { preferences ->
            preferences[CHAT_RESPONSE_KEY]
        }

    // Example function to clear DataStore (optional)
    suspend fun clearResponse() { // Renamed for consistency
        context.dataStore.edit { preferences ->
            preferences.remove(CHAT_RESPONSE_KEY)
        }
    }
}
