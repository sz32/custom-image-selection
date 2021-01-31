package com.app.image.custom.image.selection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.adapters.AdapterFolderImages
import com.app.image.custom.image.selection.util.ModelImages
import kotlinx.android.synthetic.main.frag_folder.*

class FragFolder : Fragment() {


    private var adapterAlbum: AdapterFolderImages? = null
    private var modelImages: ModelImages? = null

    companion object{
        fun newInstance(): FragFolder {
            return FragFolder()
        }
    }

    fun setModel(modelImages: ModelImages): FragFolder {
        this.modelImages = modelImages
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.frag_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        initRecy()
    }

    private fun initRecy() {
        adapterAlbum = AdapterFolderImages(context, modelImages?.allImagePath)
        recyImages?.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = adapterAlbum
        }
    }

}