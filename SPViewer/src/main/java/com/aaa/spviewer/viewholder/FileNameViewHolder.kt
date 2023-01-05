package com.aaa.spviewer.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aaa.spviewer.R

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:20:00
 * @Description: 功能描述：
 */
class FileNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvFileName: TextView
    var tvFileSize: TextView

    init {
        tvFileName = itemView.findViewById(R.id.tv_file_name)
        tvFileSize = itemView.findViewById(R.id.tv_file_size)
    }
}