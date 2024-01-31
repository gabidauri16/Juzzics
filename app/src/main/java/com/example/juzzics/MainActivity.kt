package com.example.juzzics

import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.example.juzzics.musics.ui.MusicsScreen
import com.example.juzzics.ui.theme.JuzzicsTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuzzicsTheme {
                val hasAudioPermission = remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(this, READ_MEDIA_AUDIO) ==
                                PackageManager.PERMISSION_GRANTED
                    )
                }
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasAudioPermission.value = isGranted
                    }
                )
                LaunchedEffect(hasAudioPermission) {
                    if (!hasAudioPermission.value) {
                        permissionLauncher.launch(READ_MEDIA_AUDIO)
                    }
                }
                if (hasAudioPermission.value) {
                    MusicsScreen()
                }
            }
        }
    }
}
