package com.sample.storeapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sample.storeapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    private val STORAGE_PERMISSIONS_REQUEST_CODE = 1001
    private val PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    private val PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private lateinit var mainBinding: ActivityMainBinding
    private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.lifecycleOwner = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasStoragePermissions(PERMISSION_READ_STORAGE, PERMISSION_WRITE_STORAGE)) {
                if (shouldShowRequestPermissionRationale(PERMISSION_READ_STORAGE) ||
                    shouldShowRequestPermissionRationale(PERMISSION_WRITE_STORAGE)
                ) {
                    Toast.makeText(this, "Storage permissions are required", Toast.LENGTH_LONG)
                        .show()
                }
                requestPermissions(
                    arrayOf(PERMISSION_READ_STORAGE, PERMISSION_WRITE_STORAGE),
                    STORAGE_PERMISSIONS_REQUEST_CODE
                )
            } else {
            }
        }
        val filePath = getExternalFilesDir(null)!!.path + "/myText.txt"
        Log.e("Test", "path : $filePath")

      //  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        // 버튼 클릭 시
        btn.setOnClickListener {
            writeTextToFile(filePath)
        }


        // 읽기 클릭 시
        read.setOnClickListener {
            readTextFromFile(filePath)
        }

    }  // onCreate

    fun writeTextToFile(path: String) {
        val file = File(path)
        val fileWriter = FileWriter(file, false)
        val bufferedWriter = BufferedWriter(fileWriter)

        bufferedWriter.append("Test1\n")
        bufferedWriter.append("Test2")
        bufferedWriter.newLine()
        bufferedWriter.append("Test7\n")
        bufferedWriter.close()



        Log.e("저장 되었는가?1", " : " +path)
        Log.e("저장 되었는가?2", " : " +file)
        Log.e("저장 되었는가?3", " : " +fileWriter)
        Log.e("저장 되었는가?4", " : " +bufferedWriter)
    }


    fun readTextFromFile(path: String) {
        val file = File(path)
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)

        bufferedReader.readLines().forEach() {
            Log.d("Test", it)
        }
    }

    private fun hasStoragePermissions(
        permission_read_storage: String,
        permission_write_storage: String
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(permission_read_storage) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(permission_write_storage) == PackageManager.PERMISSION_GRANTED
        } else true
    }
}