package com.example.arivemate.ui.contactInformation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arivemate.R
import com.example.arivemate.data.model.contact
import com.example.arivemate.databinding.ActivityContactBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    private lateinit var contactList : MutableList<contact>
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater) // ✅ initialize
        setContentView(binding.root)
        binding.ContactRecycler.layoutManager= LinearLayoutManager(this)
        contactList= mutableListOf()


        contactAdapter = ContactAdapter(contactList) {phoneNumner -> makePhoneCall(phoneNumner)}

        binding.ContactRecycler.adapter= contactAdapter

        database = FirebaseDatabase.getInstance().getReference("Contact_Information").child("Canada")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                for(child in snapshot.children){
                    val name = child.key?:" "
                    val phone = child.getValue().toString()
                    contactList.add(contact(name,phone))

                }
                contactAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ContactActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })



    }
    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL) // Use ACTION_CALL if you want direct call
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}