package com.example.smishingapp.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermission() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_SMS)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.hasPermission -> {
            MyAppNavHost(modifier = Modifier)
        }
        permissionState.shouldShowRationale -> {
            Column {
                Text(text = "Reading external permission is required by this app")
                permissionState.launchPermissionRequest()
            }
        }
    }
}

