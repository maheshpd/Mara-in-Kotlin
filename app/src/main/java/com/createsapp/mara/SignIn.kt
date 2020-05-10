package com.createsapp.mara

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    var email: String? = null
    var password: String? = null

    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInBtn.setOnClickListener {

            email = emailEdt.text.toString().trim()
            password = passwordEdt.text.toString().trim()

            when {
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                        auth
                }
            }
        }

        createAccBtn.setOnClickListener {
            var intent = Intent(this@SignIn,SignUp::class.java)
            startActivity(intent)
            finish()
        }

    }
}
