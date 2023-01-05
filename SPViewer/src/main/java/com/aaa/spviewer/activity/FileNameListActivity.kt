package com.aaa.spviewer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aaa.spviewer.R

class FileNameListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar?.setTitle("SP 文件列表")
    }
}