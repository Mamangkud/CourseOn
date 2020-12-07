package com.dicoding.picodiploma.courseon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_register.*

class Login : AppCompatActivity() {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            doLogin()
        }
        tv_register_here.setOnClickListener {
            startActivity(Intent(applicationContext, Register::class.java))
        }
    }

    private fun doLogin() {
        if (edt_email.text.toString().isEmpty()) {
            edt_email.error = "Please enter email"
            edt_email.requestFocus()
            return
        }
        if (edt_password.text.toString().isEmpty()) {
            edt_password.error = "Please enter password"
            edt_password.requestFocus()
            return
        }
        mAuth.signInWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        val docRef = Firebase.firestore.collection("users").document(mAuth.uid.toString())
//        val task: Task<DocumentSnapshot>? = null
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var document: DocumentSnapshot? = task?.result
                if (document!!.exists()) {
                    if (mAuth.currentUser != null) {
                        if (document?.getString("role").toString() == "Guru") {
                            startActivity(Intent(this, MainActivityGuru::class.java))
                            finish()
                        }
                        if (document?.getString("role").toString() == "Murid") {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }
            else {
                Toast.makeText(
                    baseContext, "Login failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        }
    }
