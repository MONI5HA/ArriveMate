package com.example.arivemate.ui.student_center

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arivemate.R

class country : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_country)
        val countries = listOf("Canada", "USA", "UK", "Australia")

        recyclerView = findViewById(R.id.country_grid)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CountryAdapter(countries) { country ->
            val intent = Intent(this, Checklist::class.java)
            intent.putExtra("country", country)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}