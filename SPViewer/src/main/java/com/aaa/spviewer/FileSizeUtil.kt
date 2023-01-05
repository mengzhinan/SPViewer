package com.aaa.spviewer

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:48:32
 * @Description: 功能描述：
 *
 */
object FileSizeUtil {

    fun formatFileSize(s: Long): String {
        var result = ""
        if (s < 0) {
            result = "unknown"
        } else if (s < 1024) {
            result = "$s Bytes"
        } else if (s < 1024 * 1024) {
            result = "${s * 1.0 / 1024} KB"
        } else if (s < 1024 * 1024 * 1024) {
            result = "${s * 1.0 / 1024 / 1024} MB"
        } else if (s < 1024 * 1024 * 1024 * 1024) {
            result = "${s * 1.0 / 1024 / 1024 / 1024} GB"
        } else if (s < 1024 * 1024 * 1024 * 1024 * 1024) {
            result = "${s * 1.0 / 1024 / 1024 / 1024 / 1024} TB"
        } else {
            // 无穷大
            result = "Infinity"
        }
        return result
    }

}
