package com.ccs.chartai.features

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun ChartAIApp(modifier: Modifier) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var analysis by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // handle bitmap: save to cache & set Uri
        // simplified for brevity
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                // check permission & launch camera
                // omitted for brevity
            }) {
                Text("Capture")
            }
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Upload")
            }
        }

        Button(
            onClick = {
                imageUri?.let { uri ->
                    loading = true
                    analysis = null
                    coroutineScope.launch {
//                        try {
//                            val response = Repository.uploadImageUriForAnalysis(uri)
//                            analysis = response.analysis
//                        } catch (e: Exception) {
//                            analysis = "Failed to analyze"
//                        }
                        loading = false
                    }
                }
            },
            enabled = imageUri != null && !loading
        ) {
            Text("Analyze")
        }

        if (loading) CircularProgressIndicator()
        analysis?.let { Text(it) }
    }
}