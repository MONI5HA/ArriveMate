package com.example.arivemate.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.arivemate.MainActivity
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivitySplashScreenBinding
import com.example.arivemate.ui.auth.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding =  DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        lifecycleScope.launch{
            delay(2000L)
            ButtonVisible()

        }



    }
    private fun ButtonVisible(){
        binding.SplashButton.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(500).start()
        }
        binding.SplashButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java));
        }





    }

}