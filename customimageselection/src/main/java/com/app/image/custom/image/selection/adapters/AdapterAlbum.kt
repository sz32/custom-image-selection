package com.app.image.custom.image.selection.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.util.ImagePath
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.cell_album_pic.view.*

class AdapterAlbum(var context: Context?, var imagePathList: ArrayList<ImagePath?>?, private val listener: (Int,ImagePath) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumHolderTypeOne(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_album_pic, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imagePathList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imagePath = imagePathList?.get(position)
        (holder as AlbumHolderTypeOne).bindItems(imagePath)
    }

    inner class AlbumHolderTypeOne(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(modelImages: ImagePath?) = with(itemView) {

            Glide.with(context)
                .load("file://" + modelImages?.imagePath)
                .override(500, 500)
                .thumbnail(0.3f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAlbumImage)

            chkAlbumImage?.setOnCheckedChangeListener(null)

            itemView.setOnClickListener {
                listener(adapterPosition,modelImages!!)
            }

            chkAlbumImage?.isChecked = modelImages?.isSelected!!

            chkAlbumImage?.setOnCheckedChangeListener { buttonView, isChecked ->
                listener(adapterPosition,modelImages)
            }

        }

    }
}