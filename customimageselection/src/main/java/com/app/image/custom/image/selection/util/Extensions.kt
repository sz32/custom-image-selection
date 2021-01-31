package com.app.image.custom.image.selection.util

import android.view.View
import java.net.URLConnection

fun isImageFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("image") ?: false
}

fun isVideoFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("video") ?: false
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun toast(){

}