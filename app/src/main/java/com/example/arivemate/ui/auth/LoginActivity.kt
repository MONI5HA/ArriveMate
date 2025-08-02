package com.example.arivemate.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.arivemate.MainActivity
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityLoginBinding
import com.example.arivemate.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel:AuthViewModel by viewModels();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        binding.textView5.setOnClickListener {


            startActivity(Intent(this,RegisterActivity::class.java));
        }


        binding.LoginButton.setOnClickListener {
            it.animate().scaleX(1.1f).scaleY(1.1f).setDuration(150).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
            }.start()

            loginCheck()
        }

        viewModel.authResult.observe(this){(sucess,error)->
            if (sucess){
                startActivity(Intent(this,MainActivity::class.java));
                finish()

            }
            else{
                Toast.makeText(this,error?:"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun loginCheck(){
        val log_email = binding.editTextTextEmailAddress.text.toString()
        val log_pass = binding.editTextTextPassword.text.toString()
        val log_check = binding.checkBox.isChecked
        if (log_email.isBlank() || log_pass.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (log_check){
            viewModel.login(log_email,log_pass);
        }
        else{
            Toast.makeText(this,"You need to agree the terms and condition",Toast.LENGTH_SHORT).show();
        }

    }
}