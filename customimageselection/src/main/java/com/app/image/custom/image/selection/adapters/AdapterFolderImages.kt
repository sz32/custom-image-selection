package com.app.image.custom.image.selection.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.activites.ActAlbum
import com.app.image.custom.image.selection.util.ImagePath
import com.app.image.custom.image.selection.util.gone
import com.app.image.custom.image.selection.util.isVideoFile
import com.app.image.custom.image.selection.util.visible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.cell_album_pic.view.*

class AdapterFolderImages(private var context: Context?, var imagePathList: ArrayList<ImagePath?>?, val listener: (Int, ImagePath?,Boolean) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FolderHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_album_pic, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imagePathList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FolderHolder).bindItems(imagePathList?.get(position))
    }

    inner class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(imagePath: ImagePath?) = with(itemView) {

            if (isVideoFile("file://${imagePath?.imagePath}")) {
                ivPlayIcon?.visible()
            } else {
                ivPlayIcon?.gone()
            }

            if (ActAlbum.isMultiple) {
                if (imagePath?.isSelected == true) {
                    ivCheck.visible()
                } else {
                    ivCheck.gone()
                }
                itemView.setOnClickListener {
                    listener(adapterPosition, imagePath,true)
                }
            }else{
                itemView.setOnClickListener {
                    listener(adapterPosition, imagePath,false)
                }
            }

            Glide.with(context)
                .load("file://" + imagePath?.imagePath)
                .override(500, 500)
                .thumbnail(0.3f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAlbumImage)

        }

    }
}