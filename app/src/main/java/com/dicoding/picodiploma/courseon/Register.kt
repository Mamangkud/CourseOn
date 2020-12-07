package com.dicoding.picodiploma.courseon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            startActivity(Intent(this, MainActivityGuru::class.java))
            finish()
        }

        btn_register.setOnClickListener {
            signUpUser("Murid")


        }
        btn_register_guru.setOnClickListener {
            signUpUser("Guru")
        }
        tv_login_here.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    fun signUpUser(role: String) {
        val db = Firebase.firestore
        if (edt_nama.text.toString().isEmpty()) {
            edt_nama.error = "Please enter name"
            edt_nama.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.text.toString()).matches()) {
            edt_email.error = "Please enter valid email"
            edt_email.requestFocus()
            return
        }
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
        if (edt_confirm_pass.text.toString().isEmpty()) {
            edt_confirm_pass.error = "Please enter password"
            edt_confirm_pass.requestFocus()
            return
        }

        mAuth.createUserWithEmailAndPassword(
            edt_email.text.toString(),
            edt_password.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val data = hashMapOf(
                                    "nama" to edt_nama.text.toString(),
                                    "role" to role
                                )
                                db.collection("users").document(user.uid.toString())
                                    .set(data)
                                    .addOnSuccessListener {
                                        Log.d(
                                            "Tag",
                                            "DocumentSnapshot successfully written!"
                                        )
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(
                                            "TAG",
                                            "Error writing document",
                                            e
                                        )
                                    }
                                Toast.makeText(this@Register, "User Created", Toast.LENGTH_SHORT)
                                    .show()
                                if (role == "Guru") {
                                    startActivity(Intent(this, MainActivityGuru::class.java))
                                }
                                if (role == "Murid") {
                                    startActivity(Intent(this, MainActivity::class.java))
                                }
                                updateProfile()
                                updateEmail()
                                finish()
                            }
                        }

                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun updateProfile() {

        val user = mAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(edt_nama.text.toString()).build()
        user!!.updateProfile(profileUpdates)
    }

    private fun updateEmail() {
        val user = mAuth.currentUser
        user!!.updateEmail(edt_email.text.toString())
    }
}