package com.example.arivemate.retrofit.News

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_web_view)


        binding =DataBindingUtil.setContentView(this,R.layout.activity_web_view)

        val url = intent.getStringExtra("url")?:return

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled=true
        binding.webView.loadUrl(url)
    }
}