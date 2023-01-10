package com.aaa.spviewer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaa.spviewer.R
import com.aaa.spviewer.SPDataHelper
import com.aaa.spviewer.adapter.FileContentAdapter
import com.aaa.spviewer.model.FileContentItem

class FileContentActivity : AppCompatActivity() {

    companion object {
        const val PARAM_FILE_NAME_NO_SUFFIX = "param_file_name_no_suffix"
    }

    // sp 文件名称(不包含文件后缀名)
    private var fileNameNoSuffix: String? = null

    private var btnSearch: Button? = null
    private var tvNoData: TextView? = null
    private var rvFileContent: RecyclerView? = null
    private var fileContentAdapter: FileContentAdapter? = null

    private val noDataInterface = object : FileContentAdapter.NoDataInterface {
        override fun hasData(hasData: Boolean) {
            tvNoData?.visibility = if (hasData) View.GONE else View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        fileNameNoSuffix = intent?.getStringExtra(PARAM_FILE_NAME_NO_SUFFIX)
        supportActionBar?.title = "$fileNameNoSuffix 文件内容"

        btnSearch = findViewById(R.id.btn_search)
        btnSearch?.setOnClickListener {
            search()
        }
        tvNoData = findViewById(R.id.tv_no_data)
        rvFileContent = findViewById(R.id.rv_file_content)

        tvNoData?.setOnClickListener {
            fileContentAdapter?.setData(
                SPDataHelper.getSPFileContents(this, fileNameNoSuffix), noDataInterface
            )
        }

        fileContentAdapter = FileContentAdapter()
        rvFileContent?.adapter = fileContentAdapter
        rvFileContent?.layoutManager = LinearLayoutManager(this)

        fileContentAdapter?.setOnClick(object : FileContentAdapter.OnClickInterface {
            override fun onClick(fileContentItem: FileContentItem) {
                val newIntent = Intent(this@FileContentActivity, KeyValueDetailActivity::class.java)
                newIntent.putExtra(
                    KeyValueDetailActivity.PARAM_FILE_NAME_NO_SUFFIX, fileNameNoSuffix
                )
                newIntent.putExtra(
                    KeyValueDetailActivity.PARAM_FILE_CONTENT_KEY, fileContentItem.key
                )
                newIntent.putExtra(
                    KeyValueDetailActivity.PARAM_FILE_CONTENT_VALUE, fileContentItem.value
                )
                newIntent.putExtra(
                    KeyValueDetailActivity.PARAM_FILE_CONTENT_VALUE_TYPE,
                    fileContentItem.dataTypeString
                )
                startActivity(newIntent)
            }
        })

        fileContentAdapter?.setOnLongClick(object : FileContentAdapter.OnLongClickInterface {
            @SuppressLint("NotifyDataSetChanged")
            override fun onLongClick(fileContentItem: FileContentItem) {

                AlertDialog.Builder(this@FileContentActivity).setTitle("确认")
                    .setMessage("确认要删除 ${fileContentItem.key} 健-值对吗？").setPositiveButton(
                        "确认"
                    ) { dialog, _ ->
                        val isSuccess = SPDataHelper.removeKey(
                            this@FileContentActivity, fileNameNoSuffix, fileContentItem.key
                        )
                        val msg = if (isSuccess) "删除成功" else "删除失败"
                        Toast.makeText(this@FileContentActivity, msg, Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                        fileContentAdapter?.remove(fileContentItem, noDataInterface)
                    }.setNeutralButton(
                        "取消"
                    ) { dialog, _ -> dialog?.dismiss() }.show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fileContentAdapter?.setData(
            SPDataHelper.getSPFileContents(this, fileNameNoSuffix), noDataInterface
        )
    }

    private fun search() {
        val viewRoot = LayoutInflater.from(this).inflate(R.layout.layout_dialog_search, null)
        val etSearchKey = viewRoot.findViewById<EditText>(R.id.et_search_key)
        val etSearchValue = viewRoot.findViewById<EditText>(R.id.et_search_value)
        AlertDialog.Builder(this@FileContentActivity).setTitle("搜索").setView(viewRoot)
            .setPositiveButton(
                "确认"
            ) { dialog, _ ->
                val sKey = etSearchKey.text.toString()
                val sValue = etSearchValue.text.toString()
                dialog?.dismiss()
                fileContentAdapter?.setData(
                    SPDataHelper.searchKeyAndValue(
                        this, fileNameNoSuffix, sKey, sValue
                    ), noDataInterface
                )
            }.setNeutralButton(
                "取消"
            ) { dialog, _ -> dialog?.dismiss() }.show()
    }

}
