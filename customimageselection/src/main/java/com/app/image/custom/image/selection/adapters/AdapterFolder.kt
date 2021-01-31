package com.app.image.custom.image.selection.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.image.custom.image.selection.R
import com.app.image.custom.image.selection.util.ModelImages
import kotlinx.android.synthetic.main.cell_folder.view.*

class AdapterFolder(private var context: Context?, var imagePathList: ArrayList<ModelImages>, var listener: (Int, ModelImages?) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FolderHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_folder, parent, false))
    }

    override fun getItemCount(): Int {
        return imagePathList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FolderHolder).bindItems(imagePathList[position])
    }

    inner class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(modelImages: ModelImages?) = with(itemView) {

            tvFolderName.text = modelImages?.strFolder
            if (modelImages?.isSelected == true) {
                tvFolderName?.apply {
                    this.setTypeface(null, Typeface.BOLD)
                    this.setTextColor(Color.BLACK)
                }
            } else {
                tvFolderName?.apply {
                    this.setTypeface(null, Typeface.NORMAL)
                    this.setTextColor(Color.LTGRAY)
                }
            }

            itemView.setOnClickListener {
                listener(adapterPosition, modelImages)
            }

        }

    }
}