package com.aaa.spviewer

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:48:32
 * @Description: 功能描述：
 *
 */
object FileSizeUtil {

    /**
     * 格式化文件大小
     */
    fun formatFileSize(s: Long?): String {
        if (s == null) {
            return "unknown"
        }
        var tempS = s * 1.0F
        if (s < 0) {
            return "unknown"
        }

        if (tempS < 1024) {
            return "$tempS Bytes"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "$tempS KB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "$tempS MB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "$tempS GB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "$tempS TB"
        }
        // 无穷大
        return "Infinity"
    }

}
