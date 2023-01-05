package com.aaa.spviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aaa.spviewer.R
import com.aaa.spviewer.model.FileNameItem
import com.aaa.spviewer.viewholder.FileNameViewHolder

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:20:24
 * @Description: 功能描述：
 */
class FileNameAdapter : RecyclerView.Adapter<FileNameViewHolder>() {
    private val list: List<FileNameItem> = ArrayList()
    fun setData() {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileNameViewHolder {
        return FileNameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_file_name, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FileNameViewHolder, position: Int) {
        val fileNameItem = list[position]
        holder.tvFileName.text = fileNameItem.fileName
        holder.tvFileSize.text = fileNameItem.fileSize
    }

    override fun getItemCount(): Int {
        return list.size
    }
}