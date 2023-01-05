package com.aaa.spviewer

import android.content.Context
import android.content.SharedPreferences
import java.io.File

/**
 * @Author: duke
 * @DateTime: 2023-01-04 17:36:07
 * @Description: 功能描述：sp 文件内容处理类
 *
 */
object SPDataHelper {

    private const val SHARED_PREFS = "shared_prefs"
    private const val FILE_SUFFIX = ".xml"

//    @Deprecated(
//        "过时方法",
//        ReplaceWith(
//            "context.getSharedPreferences(name, Context.MODE_PRIVATE)",
//            "android.content.Context"
//        )
//    )
//    private fun getSharedPreferences2(context: Context): SharedPreferences {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//    }

    fun getSharedPreferences(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getSPFolder(context: Context?): File? {
        // 获取 sp 所在父目录：/data/data/packageName/
        val pFile = context?.filesDir?.parentFile
        if (pFile?.exists() == false) {
            pFile.mkdirs()
        }
        if (pFile == null || !pFile.exists()) {
            return null
        }
        // 获取 sp 所在目录：/data/data/packageName/shared_prefs
        val targetFolder = File(pFile, SHARED_PREFS)
        if (!targetFolder.exists()) {
            targetFolder.mkdirs()
        }
        if (!targetFolder.exists()) {
            return null
        }
        return targetFolder
    }

    /**
     * 获取 shared_prefs 目录下所有的 sp 文件名称
     * @param context context
     * @param isNeedSuffix 是否需要携带文件名后缀
     */
    fun getSPFileNames(context: Context?, isNeedSuffix: Boolean = true): MutableList<String> {
        val nameList = mutableListOf<String>()
        // 获取 sp 所在目录：/data/data/packageName/shared_prefs
        val targetFolder = getSPFolder(context)
        val fList = targetFolder?.listFiles()
        if (fList == null || fList.isEmpty()) {
            return nameList
        }
        for (f: File in fList) {
            if (!f.exists()) {
                continue
            }
            var fName: String = f.name ?: continue
            fName = fName.trim()
            if (!isNeedSuffix && fName.endsWith(FILE_SUFFIX, true)) {
                // 不能用 replace，可能文件名中间也包含相同字符串
                // 如：com.xxx.spviewer_preferences.xml.xml
                fName = fName.substring(0, fName.length - FILE_SUFFIX.length)
            }
            if (fName.isEmpty()) {
                continue
            }
            nameList.add(fName)
        }
        return nameList
    }

}
