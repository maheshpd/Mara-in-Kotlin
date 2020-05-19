package com.createsapp.mara

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.createsapp.mara.model.UserModel
import com.createsapp.mara.ui.main.MainActivity
import com.createsapp.mara.utils.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_complete_profile.*
import java.util.*

class CompleteProfile : AppCompatActivity() {

    private var IMAGE_PICKER = 1001
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        database = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCE)
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        dialog = ProgressDialog(this)

        //photo image click
        photoImg.setOnClickListener {
            checkPermissionForImage()
        }

        dobBtn.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    dobBtn.setText("" + mDay + "/" + mMonth + "/" + mYear)
                },
                year,
                month,
                day
            )

            dpd.show()
        }


        completeBtn.setOnClickListener {

            val userModel = UserModel()
            userModel.uid = user!!.uid
            userModel.fullname = fullnameEdt.text.toString()
            userModel.dob = dobBtn.text.toString()
            userModel.gender = genderSpinner.selectedItem.toString()
            userModel.imageRef = ""

            dialog.setMessage("Please wait...")
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            database.child(user.uid)
                .setValue(userModel)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        dialog.dismiss()

                        val intent = Intent(this@CompleteProfile,
                            MainActivity::class.java)
                        startActivity(intent)
                    }
                }

        }

    }

    companion object {
        //image pick code
        private val IMAGE_PICKER_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission denied
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery()
            }
        } else {
            // system OS is >= Marshmallow
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICKER_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKER_CODE) {
            photoImg.setImageURI(data!!.data)
        }
    }

}
