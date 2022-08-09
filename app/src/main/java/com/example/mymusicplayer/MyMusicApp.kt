package com.example.mymusicplayer

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyMusicApp : Application() {

    private var progressDialog: ProgressDialog? = null

    fun showProgress(context: Activity, title: String?, message: String) {
        progressDialog = ProgressDialog.show(context, title, message)
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

}