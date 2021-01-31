package com.app.image.custom.image.selection.activites

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
import kotlinx.android.synthetic.main.act_album.*


class ActAlbum : AppCompatActivity() {


    private var adaptFolder: AdapterFolder? = null
    private var curPos: Int = 0
    private var imagesHelper: ImagesHelper? = null
    private var adaptFragPager: AdaptFragPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_album)

        imagesHelper = ImagesHelper(this)


        initFragPager()
        initRecyFolder()
    }

    private fun initRecyFolder() {
        imagesHelper?.getAllImages()?.get(0)?.isSelected = true
        adaptFolder = AdapterFolder(this@ActAlbum, imagesHelper?.getAllImages() ?: arrayListOf()) { index ->
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

        imagesHelper?.getAllImages()?.forEach { it ->
            fragList.add(FragFolder.newInstance().setModel(it))
        }

        adaptFragPager = AdaptFragPager(supportFragmentManager, fragList)
        pager?.adapter = adaptFragPager
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                imagesHelper?.getAllImages()?.get(curPos)?.isSelected = false
                curPos = position
                imagesHelper?.getAllImages()?.get(curPos)?.isSelected = true
                recyFolder.smoothScrollToPosition(curPos)
                adaptFolder?.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


}
