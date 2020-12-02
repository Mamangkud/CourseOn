package com.dicoding.picodiploma.courseon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_register.*

class Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener{
            doLogin()
        }

    }

    private fun doLogin() {
        if (edt_email.text.toString().isEmpty()){
            edt_email.error = "Please enter email"
            edt_email.requestFocus()
            return
        }
        if (edt_password.text.toString().isEmpty()){
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

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(baseContext, "Login failed",
                Toast.LENGTH_SHORT).show()
        }
    }
}