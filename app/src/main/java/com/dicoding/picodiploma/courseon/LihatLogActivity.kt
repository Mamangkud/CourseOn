package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LihatLogActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_log)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Log Pemesanan"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                //bug buka profile
                if (getRole(this).equals("Guru")) {
                    startActivity(Intent(this, MainActivityGuru::class.java))
                    return@OnNavigationItemSelectedListener true
                } else if (getRole(this).equals("Murid")){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_log -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun getRole(context: Context): String {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var role = ""
        val docRef = Firebase.firestore.collection("users").document(mAuth.uid.toString())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var document: DocumentSnapshot? = task?.result
                if (document!!.exists()) {
                    if (mAuth.currentUser != null) {
                        if (document?.getString("role").toString() == "Guru") {
                            role = "Guru"
                            finish()
                        }
                        if (document?.getString("role").toString() == "Murid") {
                            role = "Murid"
                            finish()
                        }
                    }
                }
            }
        }
        return role
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}