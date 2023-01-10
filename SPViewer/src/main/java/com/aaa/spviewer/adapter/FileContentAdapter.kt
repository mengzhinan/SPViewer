package com.aaa.spviewer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aaa.spviewer.R
import com.aaa.spviewer.model.FileContentItem
import com.aaa.spviewer.viewholder.FileContentViewHolder

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:20:24
 * @Description: 功能描述：
 */
class FileContentAdapter : RecyclerView.Adapter<FileContentViewHolder>() {
    private val list: MutableList<FileContentItem> = ArrayList()

    private var onClickInterface: OnClickInterface? = null
    private var onLongClickInterface: OnLongClickInterface? = null

    fun setOnClick(onClick: OnClickInterface?) {
        onClickInterface = onClick
    }

    fun setOnLongClick(onLongClick: OnLongClickInterface?) {
        onLongClickInterface = onLongClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(paramList: MutableList<FileContentItem>, callback: NoDataInterface?) {
        list.clear()
        list.addAll(paramList)
        notifyDataSetChanged()
        callback?.hasData(list.size > 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(item: FileContentItem, callback: NoDataInterface?) {
        list.remove(item)
        notifyDataSetChanged()
        callback?.hasData(list.size > 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileContentViewHolder {
        return FileContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_file_content, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FileContentViewHolder, position: Int) {
        val fileContentItem = list[position]
        holder.tvKey.text = fileContentItem.key
        holder.tvType.text = fileContentItem.dataTypeString
        holder.tvValue.text = fileContentItem.value
        holder.itemView.setOnClickListener {
            onClickInterface?.onClick(fileContentItem)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickInterface?.onLongClick(fileContentItem)
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
        fun onClick(fileContentItem: FileContentItem)
    }

    interface OnLongClickInterface {
        fun onLongClick(fileContentItem: FileContentItem)
    }
}