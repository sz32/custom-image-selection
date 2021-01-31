package com.app.image.custom.image.selection

import android.app.Activity
import android.content.Intent
import com.app.image.custom.image.selection.activites.ActAlbum

object OpenGallery {

    private var boolean: Boolean = false
    private var context: Activity? = null
    private var onImageSelectedResultListener: OnImageSelectedResultListener? = null

    fun init(context: Activity?): OpenGallery {
        this.context = context
        return this
    }


    fun isSelectMultiple(boolean: Boolean): OpenGallery {
        this.boolean = boolean
        return this
    }

    /*fun setListener(onImageSelectedResultListener: OnImageSelectedResultListener): OpenGallery {
        this.onImageSelectedResultListener = onImageSelectedResultListener
        return this
    }*/

    fun build() {
        context?.startActivityForResult(
            Intent(context, ActAlbum::class.java)
                .putExtra("isMultiple", boolean), 0
        )
    }


}

interface OnImageSelectedResultListener {
    fun onImageSelectedResultSuccess()
    fun onImageSelectedResultFail(s: String)
}