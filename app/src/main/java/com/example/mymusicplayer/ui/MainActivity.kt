package com.example.mymusicplayer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mymusicplayer.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!checkPermission()) {
            requestPermission()
            return
        }
    }

    private fun checkPermission(): Boolean {
        val readExtStoragePermissionStatus = ContextCompat.checkSelfPermission(this@MainActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeExtStoragePermissionStatus = ContextCompat.checkSelfPermission(this@MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return readExtStoragePermissionStatus == PackageManager.PERMISSION_GRANTED && writeExtStoragePermissionStatus == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            Toast.makeText(this@MainActivity,
                "READ AND WRITE PERMISSIONS Are REQUIRED,PLEASE ALLOW FROM SETTINGS",
                Toast.LENGTH_SHORT).show()
        } else ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
            123)
    }
}