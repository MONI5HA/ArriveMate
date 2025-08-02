package com.example.arivemate.data.repository

import androidx.appcompat.view.ActionMode.Callback
import com.example.arivemate.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("userProfile");


    fun registerUser(email:String,name:String,phone:String,address:String,dob:String,password:String,callback:(Boolean,String?)->Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val uid = auth.currentUser?.uid?:" "
                    val user = User(uid,email,name,phone,address,dob)

                    database.child(uid).setValue(user).addOnCompleteListener { task->callback(task.isSuccessful,task.exception?.message) }
                }
                else{
                    callback(false,it.exception?.message);
                }
            }
    }

    fun LoginUser(email: String,password: String,callback: (Boolean, String?) -> Unit){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { callback(it.isSuccessful,it.exception?.message) }
    }
}