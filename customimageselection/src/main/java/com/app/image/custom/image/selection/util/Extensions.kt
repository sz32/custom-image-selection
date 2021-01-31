package com.app.image.custom.image.selection.util

import android.app.Activity
import android.view.View
import android.widget.Toast
import java.net.URLConnection

private var toast: Toast? = null

fun isImageFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("image") ?: false
}

fun isVideoFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("video") ?: false
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.toast(s: String) {
    if (toast != null) {
        toast?.cancel()
    }
    toast = Toast.makeText(this, s, Toast.LENGTH_LONG)
    toast?.show()
}