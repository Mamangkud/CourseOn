package com.dicoding.picodiploma.courseon

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var rvMatpel: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private var arrayList: ArrayList<MatpelModel> = arrayListOf()
    private lateinit var adapter: ItemAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataPhoto: TypedArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Course On!"
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bn_menu)
//        buttonNavigation.selectedItemId(R.id.)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        rvMatpel = findViewById(R.id.rv_matpel)

        gridLayoutManager =
            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        rvMatpel.layoutManager = gridLayoutManager
        rvMatpel.setHasFixedSize(true)
        adapter = ItemAdapter(arrayList)
        rvMatpel.adapter = adapter
        prepare()
        addItem()
        adapter = ItemAdapter(arrayList)
        rvMatpel.adapter = adapter
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_log -> {
                //intent
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.nama_matpel)
        dataPhoto = resources.obtainTypedArray(R.array.logo_matpel)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val matpel = MatpelModel(
                dataPhoto.getResourceId(position, -1),
                dataName[position]
            )
            arrayList.add(matpel)
        }
        adapter.matpel = arrayList
    }
}


