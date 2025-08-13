package com.example.arivemate.ui.news

import NewsAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityNewsBinding
import com.example.arivemate.retrofit.News.WebView
import com.example.arivemate.viewmodel.NewsViewModel
import com.hbb20.BuildConfig


class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)

        val apiKey = "pub_ebec6769d8964c28b796873ad1495974"

        val country_name = intent.getStringExtra("country_name")?:"ca"
        Toast.makeText(this,country_name,Toast.LENGTH_SHORT).show()

        viewModel.fetchNews(country_name,apiKey)

        viewModel.article.observe(this) {articles ->
            val adapter = NewsAdapter(articles) {article->
                val intent = Intent(this,WebView::class.java)
                intent.putExtra("url",article.link)
                startActivity(intent)


        }

            binding.newRecycleview.layoutManager =LinearLayoutManager(this)
            binding.newRecycleview.adapter = adapter

        }
        viewModel.error.observe(this){
            error ->
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show()

        }


    }
}