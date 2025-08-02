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
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityRegisterBinding
import com.example.arivemate.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel:AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        binding.cardView.apply {
            alpha = 0f
            translationY = 100f
            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(200)
                .start()
        }
        binding.textView4.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java));
        }
        binding.button2.setOnClickListener {
            registerCheck();
        }


        viewModel.authResult.observe(this){(success,error)->
            if (success){
                startActivity(Intent(this, LoginActivity::class.java));
                Toast.makeText(this,"User Registered Sucessfully",Toast.LENGTH_SHORT).show();


            }
            else{
                Toast.makeText(this, error ?: "Login failed", Toast.LENGTH_SHORT).show()

            }
        }
    }


    fun registerCheck(){
        val text_email = binding.editTextTextEmailAddress2.text.toString();
        val text_name = binding.editTextText.text.toString()
        val text_phone = binding.editTextPhone.text.toString()
        val text_address = binding.editTextTextPostalAddress.text.toString()
        val text_date = binding.editTextDate.text.toString()
        val text_pass = binding.editTextTextPassword2.text.toString()
       val confirm_pass = binding.editTextTextPassword3.text.toString()
        if (confirm_pass.equals(text_pass)){
            viewModel.register(text_email,text_name,text_phone,text_address,text_date,text_pass)

        }
        else{
            Toast.makeText(this,"The password should be same",Toast.LENGTH_SHORT).show()

        }




    }

}