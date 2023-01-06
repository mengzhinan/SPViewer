package com.aaa.spviewer.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.R
import com.aaa.spviewer.SPDataHelper
import com.aaa.spviewer.model.FileContentItem

class KeyValueDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_FILE_NAME_NO_SUFFIX = "param_file_name_no_suffix"
        const val PARAM_FILE_CONTENT_KEY = "param_file_content_key"
        const val PARAM_FILE_CONTENT_VALUE = "param_file_content_value"
        const val PARAM_FILE_CONTENT_VALUE_TYPE = "param_file_content_value_type"
    }

    // sp 文件名称(不包含文件后缀名)
    private var fileNameNoSuffix: String? = null

    // sp 文件中的 key
    private var fileContentKey: String? = null

    // sp 文件中 key 对应的 value
    private var fileContentValue: String? = null

    // sp 文件中 key 对应的 value 的 type
    private var fileContentValueType: Int? = 0

    private var btnUpdate: Button? = null
    private var etValue: EditText? = null
    private var tvValue: TextView? = null
    private var tvKey: TextView? = null

    // 当前处于什么模式(编辑、查看)
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_detail)

        fileNameNoSuffix = intent?.getStringExtra(PARAM_FILE_NAME_NO_SUFFIX)
        fileContentKey = intent?.getStringExtra(PARAM_FILE_CONTENT_KEY)
        fileContentValue = intent?.getStringExtra(PARAM_FILE_CONTENT_VALUE)
        fileContentValueType = intent?.getIntExtra(PARAM_FILE_CONTENT_VALUE_TYPE, 0)

        supportActionBar?.title = "$fileNameNoSuffix [key-value] 详情"

        btnUpdate = findViewById(R.id.btn_update)
        etValue = findViewById(R.id.et_value)
        tvValue = findViewById(R.id.tv_value)
        tvKey = findViewById(R.id.tv_key)

        tvKey?.text = fileContentKey
        tvValue?.text = fileContentValue

        updateStatus()

        btnUpdate?.setOnClickListener {
            isEdit = !isEdit
            saveData()
        }
    }

    private fun updateStatus() {
        if (isEdit) {
            btnUpdate?.text = "保存"
            etValue?.visibility = View.VISIBLE
            tvValue?.visibility = View.GONE
        } else {
            btnUpdate?.text = "修改"
            etValue?.visibility = View.GONE
            tvValue?.visibility = View.VISIBLE
        }
    }

    private fun saveData() {
        val oldText = tvValue?.text?.toString() ?: ""
        val newText = etValue?.text?.toString() ?: ""
        if (newText == oldText) {
            Toast.makeText(this, "内容未更改，无需保存", Toast.LENGTH_SHORT).show()
            return
        }
        if (newText == "") {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val isSuccess = saveToSP(newText)
        if (isSuccess) {
            tvValue?.text = newText
        }
        updateStatus()
    }

    private fun saveToSP(text: String): Boolean {
        var isSuccess = true
        var msg = "保存成功"
        if (fileContentValueType == FileContentItem.DATA_TYPE_BOOLEAN) {
            try {
                SPDataHelper.putBoolean(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toBoolean()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueType == FileContentItem.DATA_TYPE_FLOAT) {
            try {
                SPDataHelper.putFloat(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toFloat()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueType == FileContentItem.DATA_TYPE_INT) {
            try {
                SPDataHelper.putInt(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toInt()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueType == FileContentItem.DATA_TYPE_LONG) {
            try {
                SPDataHelper.putLong(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toLong()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueType == FileContentItem.DATA_TYPE_STRING) {
            try {
                SPDataHelper.putString(
                    this, fileNameNoSuffix, fileContentKey ?: "", text
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else {
            msg = "保存失败，不确定应该保存为什么数据类型"
            isSuccess = false
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        return isSuccess
    }


}
