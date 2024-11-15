package com.hazrat.mediastorge

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

/**
 * @author Hazrat Ummar Shaikh
 */

class PermissionManager(
    private val activity: MainActivity
) {
    private val permissions = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
    fun requestPermissions(onPermissionsGranted: () -> Unit) {

        val requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            var allGranted = true
            for (permission in permissions.entries) {
                if (!permission.value) {
                    allGranted = false
                    break
                }

            }
            if (allGranted) {
                onPermissionsGranted()
            }else{
                Toast.makeText(activity, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
        requestPermissionLauncher.launch(permissions)
    }
}