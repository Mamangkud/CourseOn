package com.dicoding.picodiploma.courseon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth = FirebaseAuth.getInstance()

        loadUserInformation()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser==null){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
//    private fun updateUI(currentUser: FirebaseUser?){
//        if(currentUser != null){
//            startActivity(Intent(this, MainActivity::class.java))
//        }else{
//            Toast.makeText(baseContext, "Login failed",
//                Toast.LENGTH_SHORT).show()
//        }
//    }
    private fun loadUserInformation() {
        val user = mAuth.currentUser
        if (user?.uid != null)
            tv_nama.text = user.displayName
            tv_email.text = user?.email
    }
    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}