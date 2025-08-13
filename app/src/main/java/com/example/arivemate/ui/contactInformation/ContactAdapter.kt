package com.example.arivemate.ui.contactInformation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.arivemate.R
import com.example.arivemate.data.model.contact

class ContactAdapter (
    private val contacts:List<contact>,
    private val onCallClick:(String)-> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){
    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.tvName);
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone);
        val btnCall : Button = itemView.findViewById(R.id.btnCall);
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false);
        return  ContactViewHolder(view);
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val contacts = contacts[position]
        holder.tvName.text = contacts.name
        holder.tvPhone.text = contacts.phone

        holder.btnCall.setOnClickListener {
            onCallClick(contacts.phone)
        }
    }

    override fun getItemCount(): Int {
        return  contacts.size
    }





}