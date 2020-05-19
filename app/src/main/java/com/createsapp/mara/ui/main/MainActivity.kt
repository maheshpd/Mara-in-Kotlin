package com.createsapp.mara.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.createsapp.mara.R
import com.createsapp.mara.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var toolbar:Toolbar
    val recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainRecyclerView!!.setHasFixedSize(true)
        mainRecyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)



        setSupportActionBar(toolbar)

        mainViewModel.populatList.observe(this, Observer {
            val listData = it
            val adapter = MainAdapter(this!!,listData)
            recyclerView!!.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
