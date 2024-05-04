package com.ccanahuire.backgroundlocationtracker.ui.screen.tracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ccanahuire.backgroundlocationtracker.ui.theme.BackgroundLocationTrackerTheme
import com.ccanahuire.backgroundlocationtracker.ui.utils.findActivity
import com.ccanahuire.backgroundlocationtracker.ui.utils.openApplicationSettings

@Composable
fun LocationTrackerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context.findActivity()

    var locationPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var showPermissionRationale by remember {
        mutableStateOf(false)
    }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            locationPermissionGranted = granted
        }

    LaunchedEffect(Unit) {
        if (!locationPermissionGranted) {
            if (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionRationale = true
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Box(modifier = modifier) {
        if (locationPermissionGranted) {
            var trackingEnabled by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val trackingText = if (trackingEnabled) {
                    "Tracking is turned on"
                } else {
                    "Tracking is turned off"
                }
                Text(text = trackingText)
                Button(onClick = { trackingEnabled = !trackingEnabled }) {
                    val buttonTrackingText = if (trackingEnabled) {
                        "Turn Off Tracking"
                    } else {
                        "Turn On Tracking"
                    }
                    Text(text = buttonTrackingText)
                }
            }
        } else {
            LocationPermissionNotGranted(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LocationPermissionRationale(
            showRationale = showPermissionRationale,
            onDismiss = { showPermissionRationale = false },
            onConfirm = {
                showPermissionRationale = false
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        )
    }
}

@Composable
private fun LocationPermissionRationale(
    showRationale: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showRationale) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(text = "You need to grant location permissions in order to track your location.")
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(text = "Understood")
                }
            },
            modifier = modifier
        )
    }
}

@Composable
fun LocationPermissionNotGranted(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Location permissions not granted."
        )
        Button(onClick = {
            context.openApplicationSettings()
        }) {
            Text(text = "Open Settings")
        }
    }
}


@Preview
@Composable
private fun LocationTrackerScreenPreview() {
    BackgroundLocationTrackerTheme {
        LocationTrackerScreen()
    }
}