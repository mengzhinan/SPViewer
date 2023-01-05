package com.aaa.spviewer

import android.content.Context
import android.content.SharedPreferences
import com.aaa.spviewer.model.FileNameItem
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

    fun <T> putKeyValue(
        context: Context?, fileNameNoSuffix: String?, key: String, value: Any, t: T
    ) {
        val sharedEditor: SharedPreferences.Editor? =
            getSharedPreferences(context, fileNameNoSuffix)?.edit()
        when (t) {
            is Boolean -> sharedEditor?.putBoolean(key, value as Boolean)?.apply()
            is Float -> sharedEditor?.putFloat(key, value as Float)?.apply()
            is Int -> sharedEditor?.putInt(key, value as Int)?.apply()
            is Long -> sharedEditor?.putLong(key, value as Long)?.apply()
            is String -> sharedEditor?.putString(key, value as String)?.apply()
        }
    }

    fun removeKey(context: Context?, fileNameNoSuffix: String?, key: String) {
        getSharedPreferences(context, fileNameNoSuffix)?.edit()?.remove(key)?.apply()
    }

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

    fun getSharedPreferences(context: Context?, fileNameNoSuffix: String?): SharedPreferences? {
        return context?.getSharedPreferences(fileNameNoSuffix, Context.MODE_PRIVATE)
    }

    /**
     * 获取 sp 文件所在的目录文件对象
     */
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
     * 获取 shared_prefs 目录下所有的 sp 文件名称 list，不包含文件名后缀
     * @param context context
     */
    fun getSPFileNameItems(context: Context?): MutableList<FileNameItem> {
        val resultList = mutableListOf<FileNameItem>()
        // 获取 sp 所在目录：/data/data/packageName/shared_prefs
        val targetFolder = getSPFolder(context)
        val fList = targetFolder?.listFiles()
        if (fList == null || fList.isEmpty()) {
            return resultList
        }
        for (f: File in fList) {
            if (!f.exists()) {
                continue
            }
            var fName: String = f.name ?: continue
            fName = fName.trim()
            if (fName.endsWith(FILE_SUFFIX, true)) {
                // 不能用 replace，可能文件名中间也包含相同字符串
                // 如：com.xxx.spviewer_preferences.xml.xml
                fName = fName.substring(0, fName.length - FILE_SUFFIX.length)
            }
            if (fName.isEmpty()) {
                continue
            }
            val item = FileNameItem()
            item.fileName = fName
            val lFile = File(targetFolder, fName + FILE_SUFFIX)
            item.fileSize = FileSizeUtil.formatFileSize(lFile.length())
            resultList.add(item)
        }
        return resultList
    }

    /**
     * 删除 sp 文件
     */
    fun deleteSPFile(context: Context?, fileNameNoSuffix: String?): Boolean {
        val folder = getSPFolder(context) ?: return false
        val targetFile = File(folder, fileNameNoSuffix + FILE_SUFFIX)
        if (!targetFile.exists()) {
            return false
        }
        targetFile.delete()
        return true
    }

}
