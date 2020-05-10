package com.createsapp.mara

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_complete_profile.*
import java.text.SimpleDateFormat
import java.util.*

class CompleteProfile : AppCompatActivity() {

    private var IMAGE_PICKER = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        //photo image click
        photoImg.setOnClickListener {
            checkPermissionForImage()
        }

        dobBtn.setOnClickListener {

            var cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)

                dobBtn.text = sdf.format(cal.time)
            }

        }
    }

    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) && (checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(permission, 12)
                requestPermissions(permissionCoarse, 13)

            } else {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        var intent = Intent(Intent.ACTION_CHOOSER)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICKER)
    }
}
