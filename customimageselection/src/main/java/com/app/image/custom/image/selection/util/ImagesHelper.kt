package com.app.image.custom.image.selection.util

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import java.math.MathContext

class ImagesHelper(private val context: Activity) {

    private var allImages: ArrayList<ModelImages> = arrayListOf()
    private var booleanFolder = false

    init {
        getAllImagesPath()
    }

    fun getAllImages(): ArrayList<ModelImages> {
        return allImages
    }

    private fun getAllImagesPath(): ArrayList<ModelImages>? {
        allImages = arrayListOf()
        allImages.clear()
        var intPosition = 0
        val cursor: Cursor?
        val columnIndexData: Int
        val columnIndexFolderName: Int
        var absolutePathOfImage: String?
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy: String = MediaStore.Images.Media.DATE_TAKEN
        cursor = context.contentResolver.query(uri, projection, null, null, "$orderBy DESC")
        columnIndexData = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)!!
        columnIndexFolderName =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            if (!cursor.getString(columnIndexData).contains("0/Android/")) {
                absolutePathOfImage = cursor.getString(columnIndexData)
                for (i in 0 until allImages.size) {
                    if (allImages[i].strFolder == cursor.getString(columnIndexFolderName)) {
                        booleanFolder = true
                        intPosition = i
                        break
                    } else {
                        booleanFolder = false
                    }
                }
                if (booleanFolder) {
                    val allPath: ArrayList<ImagePath?> = ArrayList()
                    allPath.addAll(allImages[intPosition].allImagePath!!)
                    allPath.add(ImagePath(absolutePathOfImage, false))
                    allImages[intPosition].allImagePath = allPath
                } else {
                    val allPath: ArrayList<ImagePath?> = ArrayList()
                    allPath.add(ImagePath(absolutePathOfImage, false))
                    val modelImages = ModelImages(cursor.getString(columnIndexFolderName), allPath)
                    allImages.add(modelImages)
                }
            }

        }
        allImages.add(0, ModelImages("Recent", getRecentImages()))
        allImages.add((allImages.size), ModelImages("Video", getAllVideos()))
        return allImages
    }

    private fun getRecentImages(): ArrayList<ImagePath?> {
        val recentImageUrls: ArrayList<ImagePath?> = ArrayList()
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID
        ) //get all columns of type images
        val orderBy = MediaStore.Images.Media.DATE_TAKEN //order data by date
        val imagecursor: Cursor = context.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, "$orderBy DESC")
        for (i in 0 until imagecursor.count) {
            imagecursor.moveToPosition(i)
            val dataColumnIndex: Int = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA) //get column index
            if (!imagecursor.getString(dataColumnIndex).toString().contains("0/Android/")) {
                recentImageUrls.add(ImagePath(imagecursor.getString(dataColumnIndex), false)) //get Image from column index
            }
        }
        return recentImageUrls
    }

    private fun getAllVideos(): ArrayList<ImagePath?> {
        val recentImageUrls: ArrayList<ImagePath?> = ArrayList()
        val columns = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID) //get all columns of type images
        val orderBy = MediaStore.Video.Media.DATE_TAKEN //order data by date
        val imageCursor: Cursor = context.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, "$orderBy DESC")
        for (i in 0 until imageCursor.count) {
            imageCursor.moveToPosition(i)
            val dataColumnIndex: Int = imageCursor.getColumnIndex(MediaStore.Video.Media.DATA) //get column index
            recentImageUrls.add(ImagePath(imageCursor.getString(dataColumnIndex), false)) //get Image from column index
        }
        return recentImageUrls
    }

}