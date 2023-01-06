package com.aaa.spviewer.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.R

class KeyValueDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_FILE_NAME_NO_SUFFIX = "param_file_name_no_suffix"
        const val PARAM_FILE_CONTENT_KEY = "param_file_content_key"
        const val PARAM_FILE_CONTENT_VALUE = "param_file_content_value"
    }

    // sp 文件名称(不包含文件后缀名)
    private var fileNameNoSuffix: String? = null

    // sp 文件中的 key
    private var fileContentKey: String? = null

    // sp 文件中 key 对应的 value
    private var fileContentValue: String? = null

    private var tvKey: TextView? = null
    private var tvValue: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_detail)

        fileNameNoSuffix = intent?.getStringExtra(PARAM_FILE_NAME_NO_SUFFIX)
        fileContentKey = intent?.getStringExtra(PARAM_FILE_CONTENT_KEY)
        fileContentValue = intent?.getStringExtra(PARAM_FILE_CONTENT_VALUE)

        supportActionBar?.title = "$fileNameNoSuffix key-value 详情"

        tvKey = findViewById(R.id.tv_key)
        tvValue = findViewById(R.id.tv_value)

        tvKey?.text = fileContentKey
        tvValue?.text = fileContentValue
    }
}
