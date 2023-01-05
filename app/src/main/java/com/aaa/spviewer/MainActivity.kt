package com.aaa.spviewer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aaa.spviewer.activity.FileNameListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        SPDataHelper.putBoolean(this, "aaa", "is_loaded", true)
        SPDataHelper.putFloat(this, "aaa", "money", 1.99f)
        SPDataHelper.putInt(this, "aaa", "length", 207)
        SPDataHelper.putLong(this, "aaa", "max_l", 2222201414)
        SPDataHelper.putString(this, "aaa", "full_path", "a_b_c")


        SPDataHelper.putBoolean(this, "bb1", "is_loaded1", true)
        SPDataHelper.putFloat(this, "bb1", "money1", 1.99f)
        SPDataHelper.putInt(this, "bb1", "length1", 207)
        SPDataHelper.putLong(this, "bb1", "max_l1", 2222201414)
        SPDataHelper.putString(this, "bb1", "full_path1", "a_b_c")


        startActivity(Intent(this, FileNameListActivity::class.java))
        finish()

    }
}
