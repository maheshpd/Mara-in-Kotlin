package com.createsapp.mara

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.createsapp.mara.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    var email: String? = null
    var password: String? = null

    private lateinit var auth: FirebaseAuth;
    private lateinit var dialog: ProgressDialog
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)
        signInBtn.setOnClickListener {

            email = emailEdt.text.toString().trim()
            password = passwordEdt.text.toString().trim()

            when {
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    dialog.setMessage("Please wait...")
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.show()

                    auth.signInWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                dialog.dismiss()
                                val intent = Intent(this@SignIn, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                dialog.dismiss()
                                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }

        createAccBtn.setOnClickListener {
            var intent = Intent(this@SignIn, SignUp::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStop() {
//        if (listener != null)
//            auth.removeAuthStateListener(listener)
        compositeDisposable.clear()
        super.onStop()
    }


    override fun onStart() {
        super.onStart()
//        auth.addAuthStateListener(listener)
        val user = auth.currentUser

        if (user != null) {
            goToMainPage()
        } else {

        }


    }

    private fun goToMainPage() {
        val intent = Intent(this@SignIn, MainActivity::class.java)
        startActivity(intent)
    }

}
