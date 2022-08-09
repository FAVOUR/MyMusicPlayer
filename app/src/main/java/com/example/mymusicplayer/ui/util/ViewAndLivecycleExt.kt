package com.example.mymusicplayer.ui.util

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mymusicplayer.MyMusicApp
import com.example.mymusicplayer.R

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun Activity.navigateTo(navId: Int = R.id.home_main_nav, destId: Int) {
    let {
        Navigation.findNavController(it, navId)
            .navigate(destId)
    }
}

fun Activity.navigateWithArgsTo(navId: Int = R.id.home_main_nav, destId: Int, args: Bundle) {
    this.let {
        Navigation.findNavController(it, navId)
            .navigate(destId, args)
    }
}


fun Activity.showProgress(title: String?, message: String) =
    (this.applicationContext as MyMusicApp).showProgress(this, title, message)

fun Activity.dismissProgress() = (this.applicationContext as MyMusicApp).dismissProgress()

fun Activity.onErrorMessage(title: String, throwable: Throwable) {
    dismissProgress()
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(throwable.localizedMessage)
        .setNegativeButton(android.R.string.cancel, null)
        .show()
}


/*

fun <T : Drawable> T.bytesEqualTo(t: T?) = toBitmap().bytesEqualTo(t?.toBitmap(), true)


fun Bitmap.bytesEqualTo(otherBitmap: Bitmap?, shouldRecycle: Boolean = false) = otherBitmap?.let { other ->
    if (width == other.width && height == other.height) {
        val res = toBytes().contentEquals(other.toBytes())
        if (shouldRecycle) {
            doRecycle().also { otherBitmap.doRecycle() }
        }
        res
    } else false
} ?: kotlin.run { false }


fun Bitmap.doRecycle() {
    if (!isRecycled) recycle()
}

fun <T : Drawable> T.toBitmap(): Bitmap {
    if (this is BitmapDrawable) return bitmap

    val drawable: Drawable = this
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Bitmap.toBytes(): ByteArray = ByteArrayOutputStream().use { stream ->
    compress(Bitmap.CompressFormat.JPEG, 100, stream)
    stream.toByteArray()
}

*/

