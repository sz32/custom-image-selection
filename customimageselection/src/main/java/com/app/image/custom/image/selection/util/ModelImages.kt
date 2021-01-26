package com.app.image.custom.image.selection.util

data class ModelImages(
    var strFolder: String? = null,
    var allImagePath: ArrayList<ImagePath?>? = null
)

data class ImagePath(
    val imagePath: String? = "",
    var isSelected: Boolean? = false
)