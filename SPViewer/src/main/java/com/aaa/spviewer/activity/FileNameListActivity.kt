package com.aaa.spviewer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaa.spviewer.R
import com.aaa.spviewer.SPDataHelper
import com.aaa.spviewer.adapter.FileNameAdapter
import com.aaa.spviewer.model.FileNameItem

class FileNameListActivity : AppCompatActivity() {

    private var tvNoData: TextView? = null
    private var rvFileName: RecyclerView? = null
    private var fileNameAdapter: FileNameAdapter? = null

    private val noDataInterface = object : FileNameAdapter.NoDataInterface {
        override fun hasData(hasData: Boolean) {
            tvNoData?.visibility = if (hasData) View.GONE else View.VISIBLE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)
        supportActionBar?.title = "SP 文件列表"

        tvNoData = findViewById(R.id.tv_no_data)
        rvFileName = findViewById(R.id.rv_file_name)

        tvNoData?.setOnClickListener {
            fileNameAdapter?.setData(
                SPDataHelper.getSPFileNameItems(this), noDataInterface
            )
        }

        fileNameAdapter = FileNameAdapter()
        rvFileName?.adapter = fileNameAdapter
        rvFileName?.layoutManager = LinearLayoutManager(this)

        fileNameAdapter?.setOnClick(object : FileNameAdapter.OnClickInterface {
            override fun onClick(fileNameItem: FileNameItem) {
                val newIntent = Intent(this@FileNameListActivity, FileContentActivity::class.java)
                newIntent.putExtra(
                    FileContentActivity.PARAM_FILE_NAME_NO_SUFFIX, fileNameItem.fileName
                )
                startActivity(newIntent)
            }
        })

        fileNameAdapter?.setOnLongClick(object : FileNameAdapter.OnLongClickInterface {
            @SuppressLint("NotifyDataSetChanged")
            override fun onLongClick(fileNameItem: FileNameItem) {

                AlertDialog.Builder(this@FileNameListActivity).setTitle("确认")
                    .setMessage("确认要删除文件 ${fileNameItem.fileName} 吗？").setPositiveButton(
                        "确认"
                    ) { dialog, _ ->
                        val isSuccess = SPDataHelper.deleteSPFile(
                            this@FileNameListActivity, fileNameItem.fileName
                        )
                        val msg = if (isSuccess) "删除成功" else "删除失败"
                        Toast.makeText(this@FileNameListActivity, msg, Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                        fileNameAdapter?.remove(fileNameItem, noDataInterface)
                    }.setNeutralButton(
                        "取消"
                    ) { dialog, _ -> dialog?.dismiss() }.show()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        fileNameAdapter?.setData(
            SPDataHelper.getSPFileNameItems(this), noDataInterface
        )
    }


}
