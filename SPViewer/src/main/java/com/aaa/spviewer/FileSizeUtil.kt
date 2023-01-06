package com.aaa.spviewer

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:48:32
 * @Description: 功能描述：
 *
 */
object FileSizeUtil {

    private fun getFloatNoMoreThanTwoDigits(number: Float): String {
        val format = DecimalFormat("#.##")
        // 舍弃规则，RoundingMode.FLOOR 表示直接舍弃。
        format.roundingMode = RoundingMode.HALF_UP
        return format.format(number)
    }

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
            return "${getFloatNoMoreThanTwoDigits(tempS)} Bytes"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "${getFloatNoMoreThanTwoDigits(tempS)} KB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "${getFloatNoMoreThanTwoDigits(tempS)} MB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "${getFloatNoMoreThanTwoDigits(tempS)} GB"
        }

        tempS /= 1024
        if (tempS < 1024) {
            return "${getFloatNoMoreThanTwoDigits(tempS)} TB"
        }
        // 无穷大
        return "Infinity"
    }

}
