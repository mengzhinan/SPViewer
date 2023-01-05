package com.aaa.spviewer.adapter

import android.annotation.SuppressLint
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
    private val list: MutableList<FileNameItem> = ArrayList()

    private var onClickInterface: OnClickInterface? = null
    private var onLongClickInterface: OnLongClickInterface? = null

    fun setOnClick(onClick: OnClickInterface?) {
        onClickInterface = onClick
    }

    fun setOnLongClick(onLongClick: OnLongClickInterface?) {
        onLongClickInterface = onLongClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(paramList: MutableList<FileNameItem>, callback: NoDataInterface?) {
        list.clear()
        list.addAll(paramList)
        notifyDataSetChanged()
        callback?.hasData(list.size > 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(item: FileNameItem, callback: NoDataInterface?) {
        list.remove(item)
        notifyDataSetChanged()
        callback?.hasData(list.size > 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileNameViewHolder {
        return FileNameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_file_name, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FileNameViewHolder, position: Int) {
        val fileNameItem = list[position]
        holder.tvFileName.text = fileNameItem.fileName
        holder.tvFileSize.text = fileNameItem.fileSize
        holder.itemView.setOnClickListener {
            onClickInterface?.onClick(fileNameItem)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickInterface?.onLongClick(fileNameItem)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface NoDataInterface {
        fun hasData(hasData: Boolean)
    }

    interface OnClickInterface {
        fun onClick(fileNameItem: FileNameItem)
    }

    interface OnLongClickInterface {
        fun onLongClick(fileNameItem: FileNameItem)
    }
}