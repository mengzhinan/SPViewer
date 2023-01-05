package com.aaa.spviewer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.R

class FileContentActivity : AppCompatActivity() {

    companion object {
        const val PARAM_FILE_NAME_NO_SUFFIX = "param_file_name_no_suffix"
    }

    private var fileNameNoSuffix: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        fileNameNoSuffix = intent?.getStringExtra(PARAM_FILE_NAME_NO_SUFFIX)
    }
}