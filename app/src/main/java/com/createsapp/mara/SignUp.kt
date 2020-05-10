package com.createsapp.mara

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)

        loginTxt.setOnClickListener {
            var intent = Intent(this@SignUp, SignIn::class.java)
            startActivity(intent)
            finish()
        }


        signupBTn.setOnClickListener {
            email = emailEdt.text.trim().toString()
            password = passwordEdt.text.trim().toString()
            confirmPassword = confirmPassEdt.text.trim().toString()

            when {
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(confirmPassword) -> {
                    Toast.makeText(this, "Please enter confirm Password", Toast.LENGTH_SHORT).show()
                }
                !password.equals(confirmPassword) -> {
                    Toast.makeText(this, "Password are not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    dialog.setMessage("Please wait...")
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.show()

                    auth.createUserWithEmailAndPassword(email!!, confirmPassword!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dialog.dismiss()
                                var intent = Intent(this@SignUp, CompleteProfile::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                dialog.dismiss()
                                Toast.makeText(this, "Authication failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }

    }
}
