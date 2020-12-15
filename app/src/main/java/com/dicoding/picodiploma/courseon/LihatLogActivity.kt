package com.dicoding.picodiploma.courseon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class LihatLogActivity : AppCompatActivity() {
    private lateinit var rvPesanan: RecyclerView
    private lateinit var adapter: PemesananAdapter
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("pemesanan")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_log)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Log Pemesanan"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val namaPemesan: String = mAuth.currentUser?.displayName.toString()
        firebaseFirestore = FirebaseFirestore.getInstance()
        rvPesanan = findViewById(R.id.rv_log_pesanan)
        val query = collectionReference
            .whereEqualTo("nama_pemesan", namaPemesan)
            .orderBy("priority", Query.Direction.ASCENDING)
            .orderBy("tanggal", Query.Direction.DESCENDING)



        val options = FirestoreRecyclerOptions.Builder<PemesananModel>()
            .setQuery(query, PemesananModel::class.java)
            .build()
        adapter = PemesananAdapter(options, this)
        rvPesanan.layoutManager =
            LinearLayoutManager(this@LihatLogActivity)
        rvPesanan.setHasFixedSize(true)
        rvPesanan.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter!!.stopListening()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    //bug buka profile
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}