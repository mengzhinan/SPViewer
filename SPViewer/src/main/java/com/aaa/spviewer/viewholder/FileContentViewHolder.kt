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
class FileContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvKey: TextView
    var tvType: TextView
    var tvValue: TextView

    init {
        tvKey = itemView.findViewById(R.id.tv_key)
        tvType = itemView.findViewById(R.id.tv_type)
        tvValue = itemView.findViewById(R.id.tv_value)
    }
}