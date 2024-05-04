package com.ccanahuire.backgroundlocationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.ccanahuire.backgroundlocationtracker.ui.screen.tracker.LocationTrackerScreen
import com.ccanahuire.backgroundlocationtracker.ui.theme.BackgroundLocationTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundLocationTrackerTheme {
                LocationTrackerScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
