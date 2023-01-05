package com.zhihu.spviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aaa.spviewer.SPDataHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val  v = SPDataHelper.getSPFileNames(this)
        Log.e("fa","111")

    }
}
