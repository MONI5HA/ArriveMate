package com.example.arivemate.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arivemate.data.repository.AuthRepository

class AuthViewModel:ViewModel() {

    private val authRepository=AuthRepository()

    val authResult = MutableLiveData<Pair<Boolean,String?>>()

    fun register(email:String,name:String,phone:String,address:String,dob:String,password:String){
        authRepository.registerUser(email,name,phone,address,dob,password)  { sucess,error ->
            authResult.postValue(Pair(sucess,error))
        }

    }

    fun login(email: String,password: String){
        authRepository.LoginUser(email,password) {sucess,error->
            authResult.postValue(Pair(sucess,error))
        }
    }
}