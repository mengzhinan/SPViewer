package com.aaa.spviewer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.activity.FileNameListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SPDataHelper.putKeyValue(this, "aaa", "a1", "11", String)
        SPDataHelper.putKeyValue(this, "aaa", "a1", "22", String)
        SPDataHelper.putKeyValue(this, "aaa", "a3", "33", Float)
        SPDataHelper.putKeyValue(this, "bbb.xml", "bb1", "bba", String)
        SPDataHelper.putKeyValue(this, "bbb.xml", "bb2", "95", Int)
        SPDataHelper.putKeyValue(this, "bbb.xml", "bb3", "false", Boolean)

        startActivity(Intent(this, FileNameListActivity::class.java))

    }
}
