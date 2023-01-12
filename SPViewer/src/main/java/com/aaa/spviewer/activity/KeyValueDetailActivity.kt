package com.aaa.spviewer.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.R
import com.aaa.spviewer.SPDataHelper
import java.util.*

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

    // sp 文件中 key 对应的 value 的 type 类型名称
    private var fileContentValueDataType: String? = ""

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
        fileContentValueDataType = intent?.getStringExtra(PARAM_FILE_CONTENT_VALUE_TYPE)

        supportActionBar?.title = "$fileNameNoSuffix [key-value] 详情"

        btnUpdate = findViewById(R.id.btn_update)
        etValue = findViewById(R.id.et_value)
        tvValue = findViewById(R.id.tv_value)
        tvKey = findViewById(R.id.tv_key)

        btnUpdate?.setOnClickListener {
            isEdit = !isEdit
            saveData()
        }

        tvKey?.text = fileContentKey
        tvValue?.text = fileContentValue
        etValue?.setText(fileContentValue)

        updateStatus()
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
        updateStatus()
        if (isEdit) {
            // 从「查看模式」切换到「编辑模式」，等待用户修改内容
            return
        }
        val oldText = tvValue?.text?.toString() ?: ""
        var newText = etValue?.text?.toString() ?: ""
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
            // 更新查看模式的值
            if (fileContentValueDataType == SPDataHelper.DATA_TYPE_BOOLEAN_STRING) {
                newText = newText.lowercase(Locale.getDefault())
                etValue?.setText(newText)
            }
            tvValue?.text = newText
        } else {
            // 恢复编辑模式的值
            etValue?.setText(tvValue?.text?.toString())
        }
        hideKeyboard(this, etValue)
    }

    private fun saveToSP(text: String): Boolean {
        var isSuccess = true
        var msg = "保存成功"
        if (fileContentValueDataType == SPDataHelper.DATA_TYPE_BOOLEAN_STRING) {
            try {
                val newText = text.lowercase(Locale.getDefault())
                if (newText != "true" && newText != "false") {
                    throw IllegalArgumentException("$text can not parse to Boolean")
                }
                SPDataHelper.putBoolean(
                    this, fileNameNoSuffix, fileContentKey ?: "", newText.toBoolean()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueDataType == SPDataHelper.DATA_TYPE_FLOAT_STRING) {
            try {
                SPDataHelper.putFloat(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toFloat()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueDataType == SPDataHelper.DATA_TYPE_INT_STRING) {
            try {
                SPDataHelper.putInt(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toInt()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueDataType == SPDataHelper.DATA_TYPE_LONG_STRING) {
            try {
                SPDataHelper.putLong(
                    this, fileNameNoSuffix, fileContentKey ?: "", text.toLong()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "保存失败，${e.message}"
                isSuccess = false
            }
        } else if (fileContentValueDataType == SPDataHelper.DATA_TYPE_STRING_STRING) {
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

    private fun hideKeyboard(context: Context, v: View?) {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
        imm.hideSoftInputFromWindow(v?.windowToken, 0)

    }

}
