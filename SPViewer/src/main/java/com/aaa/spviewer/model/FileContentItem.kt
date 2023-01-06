package com.aaa.spviewer.model

/**
 * @Author: duke
 * @DateTime: 2023-01-05 15:29:05
 * @Description: 功能描述：
 */
class FileContentItem {
    companion object {
        const val DATA_TYPE_BOOLEAN = 1
        const val DATA_TYPE_FLOAT = 2
        const val DATA_TYPE_INT = 3
        const val DATA_TYPE_LONG = 4
        const val DATA_TYPE_STRING = 5
    }

    var key: String? = null
    var value: String? = null
    var dataType: Int = 0
}
