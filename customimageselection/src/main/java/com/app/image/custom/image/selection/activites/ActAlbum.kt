package com.app.image.custom.image.selection.activites

import android.Manifest.*
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.adapters.AdaptFragPager
import com.app.image.custom.image.selection.adapters.AdapterFolder
import com.app.image.custom.image.selection.fragments.FragFolder
import com.app.image.custom.image.selection.util.ImagesHelper
import com.app.image.custom.image.selection.util.ModelImages
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.act_album.*
import java.util.*


class ActAlbum : AppCompatActivity() {


    private var allImages: ArrayList<ModelImages>? = arrayListOf()
    private var adaptFolder: AdapterFolder? = null
    private var curPos: Int = 0
    private var adaptFragPager: AdaptFragPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_album)



        Permissions.check(this, arrayOf(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE), null, null, object : PermissionHandler() {
            @SuppressLint("MissingPermission")
            override fun onGranted() {
                val imagesHelper = ImagesHelper(this@ActAlbum)
                allImages = imagesHelper.getAllImages()
                initFragPager()
                initRecyFolder()
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
                finish()
            }
        })

    }

    private fun initRecyFolder() {
        allImages?.getOrNull(0)?.isSelected = true
        adaptFolder = AdapterFolder(this@ActAlbum, allImages ?: arrayListOf()) { index ->
            pager.setCurrentItem(index, true)
        }
        recyFolder?.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adaptFolder
        }
    }

    private fun initFragPager() {
        val fragList = arrayListOf<Fragment>()

        allImages?.forEach { it ->
            fragList.add(FragFolder.newInstance().setModel(it))
        }

        adaptFragPager = AdaptFragPager(supportFragmentManager, fragList)
        pager?.adapter = adaptFragPager
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                allImages?.get(curPos)?.isSelected = false
                curPos = position
                allImages?.get(curPos)?.isSelected = true
                recyFolder.smoothScrollToPosition(curPos)
                adaptFolder?.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


}
