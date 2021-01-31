package com.app.image.custom.image.selection.util

import java.io.Serializable

data class ModelImages(
    var strFolder: String? = null,
    var allImagePath: ArrayList<ImagePath?>? = null,
    var isSelected: Boolean = false
):Serializable

data class ImagePath(
    val imagePath: String? = "",
    var isSelected: Boolean? = false
):Serializable