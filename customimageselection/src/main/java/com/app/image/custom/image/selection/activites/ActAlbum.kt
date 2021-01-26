package com.app.image.custom.image.selection.activites

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.adapters.AdapterAlbum
import com.app.image.custom.image.selection.adapters.CustomDropDownAdapter
import com.app.image.custom.image.selection.util.ImagePath
import com.app.image.custom.image.selection.util.ModelImages
import kotlinx.android.synthetic.main.act_album.*
import java.util.jar.Manifest


class ActAlbum : AppCompatActivity() {

    private var allImages: ArrayList<ModelImages> = ArrayList()
    private var booleanFolder = false
    private var adapterAlbum: AdapterAlbum? = null
    private var spinSelPos = 0
    private var imagePathList: ArrayList<ImagePath?>? = ArrayList()
    private var selectedImageList: ArrayList<String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_album)

        getAllImagesPath()
        initListeners()
        initSpinnerAdapter()
        initRecycler()
    }

    private fun initListeners() {
        spinnerAlbum?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                imagePathList?.clear()
                imagePathList?.addAll(allImages[position].allImagePath!!)
                adapterAlbum?.notifyDataSetChanged()
            }
        }

        tvDone?.setOnClickListener {
/*
            val intent = Intent()
            intent.putExtra(ALBUM_SEL_IMAGE, selectedImageList)
            setResult(Activity.RESULT_OK, intent)
            finish()
*/
        }
    }

    private fun initRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        imagePathList?.addAll(allImages[spinSelPos].allImagePath!!)
        adapterAlbum = AdapterAlbum(this, imagePathList) {index,it->
            it.isSelected = !it.isSelected!!
            if (it.isSelected!!) {
                selectedImageList?.add(it.imagePath!!)
            } else {
                selectedImageList?.remove(it.imagePath!!)
            }
            adapterAlbum?.notifyItemChanged(index)
        }

        recyAlbum.apply {
            hasFixedSize()
            layoutManager = gridLayoutManager
            adapter = adapterAlbum
        }


    }

    private fun initSpinnerAdapter() {
        val spinnerAdapt = CustomDropDownAdapter(this, allImages)
        spinnerAlbum.adapter = spinnerAdapt
    }

    private fun getAllImagesPath(): ArrayList<ModelImages>? {
        allImages.clear()
        var intPosition = 0
        val cursor: Cursor?
        val columnIndexData: Int
        val columnIndexFolderName: Int
        var absolutePathOfImage: String?
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy: String = MediaStore.Images.Media.DATE_TAKEN
        cursor = applicationContext.contentResolver.query(uri, projection, null, null, "$orderBy DESC")
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
        val imagecursor: Cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, "$orderBy DESC")
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
        val imageCursor: Cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, "$orderBy DESC")
        for (i in 0 until imageCursor.count) {
            imageCursor.moveToPosition(i)
            val dataColumnIndex: Int = imageCursor.getColumnIndex(MediaStore.Video.Media.DATA) //get column index
            recentImageUrls.add(ImagePath(imageCursor.getString(dataColumnIndex), false)) //get Image from column index
        }
        return recentImageUrls
    }

}
