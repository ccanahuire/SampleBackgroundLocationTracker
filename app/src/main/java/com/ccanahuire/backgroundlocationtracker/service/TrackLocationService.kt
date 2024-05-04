package com.ccanahuire.backgroundlocationtracker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TrackLocationService : Service() {

//    private lateinit var fusedLocationClient: Fused
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}