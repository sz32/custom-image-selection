package com.app.image.selection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.image.custom.image.selection.util.ImagePath
import com.app.image.custom.image.selection.util.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        com.app.image.custom.image.selection.OpenGallery
            .init(this)
            .isSelectMultiple(true)
            /*.setListener(object : OnImageSelectedResultListener {
                override fun onImageSelectedResultSuccess() {

                }

                override fun onImageSelectedResultFail(s: String) {

                }
            })*/.build()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.extras != null) {
            toast((data.extras?.getSerializable("DATA") as ArrayList<ImagePath>).size.toString())
        }
    }

}