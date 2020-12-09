package com.dicoding.picodiploma.courseon

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_matpel.*

class MatpelActivity : AppCompatActivity() {
    private lateinit var rvGuru: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private var arrayList: ArrayList<GuruModel> = arrayListOf()
    private lateinit var adapter: GuruAdapter
    private lateinit var guruName: Array<String>
    private lateinit var tanggal: Array<String>
    private lateinit var waktu: Array<String>

    //    private lateinit var dataPhoto: TypedArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matpel)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "List Guru"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        rvGuru = findViewById(R.id.rv_list_guru)

        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this@MatpelActivity)
        rvGuru.layoutManager = layoutManager
        rvGuru.setHasFixedSize(true)
        adapter = GuruAdapter(arrayList)
        rvGuru.adapter = adapter
        prepare()
        addItem()
        adapter = GuruAdapter(arrayList)
        rvGuru.adapter = adapter
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (getRole(this).equals("Guru")) {
                        startActivity(Intent(this, MainActivityGuru::class.java))
                        return@OnNavigationItemSelectedListener true
                    } else if (getRole(this).equals("Murid")) {
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

    private fun prepare() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val docRef = Firebase.firestore.collection("jadwal")

        //GANTI JADI NGAMBIL KE DB

        guruName = resources.getStringArray(R.array.nama_matpel)
        tanggal = resources.getStringArray(R.array.nama_matpel)
        waktu = resources.getStringArray(R.array.nama_matpel)

    }

    private fun addItem() {
        for (position in guruName.indices) {
            val guru = GuruModel(
                guruName[position], tanggal[position], waktu[position]
            )
            arrayList.add(guru)
        }
        adapter.guru = arrayList
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