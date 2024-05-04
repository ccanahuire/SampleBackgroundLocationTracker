package com.ccanahuire.backgroundlocationtracker.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun shouldShowPermissionRationale(permission: String): Boolean {
    val activity = LocalContext.current.findActivity()
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
}

fun Context.findActivity(): Activity {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        } else {
            currentContext = currentContext.baseContext
        }
    }
    throw IllegalStateException("Can't find an Activity in this Context.")
}

fun Context.openApplicationSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also {
        startActivity(it)
    }
}
