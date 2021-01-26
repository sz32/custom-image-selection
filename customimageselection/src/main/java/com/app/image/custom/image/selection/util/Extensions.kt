package com.app.image.custom.image.selection.util

import java.net.URLConnection

private fun isImageFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("image") ?: false
}

private fun isVideoFile(path: String?): Boolean {
    val mimeType: String? = URLConnection.guessContentTypeFromName(path)
    return mimeType?.startsWith("video") ?: false
}
