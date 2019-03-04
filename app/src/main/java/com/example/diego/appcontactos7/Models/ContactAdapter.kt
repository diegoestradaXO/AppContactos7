package com.example.diego.appcontactos7.Models

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diego.appcontactos7.R


class ContactAdapter() : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK) {

    //ADAPTADOR PARA MOSTRAR EN EL RECYCLE VIEW DEL MAIN ACTIVITY

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areContentsTheSame(p0: Contact, p1: Contact): Boolean {
                return p0.name == p1.name && p0.phone == p1.phone && p0.email==p1.email && p0.priority == p1.priority
            }

            override fun areItemsTheSame(p0: Contact, p1: Contact): Boolean {
                return p0.id == p1.id
            }
        }
    }

    private var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentContact: Contact = getItem(position)

        holder.textViewTitle.text = currentContact.name
        holder.textViewTelefono.text = currentContact.phone
        holder.textViewEmail.text = currentContact.email
    }

    fun getContactAt(position: Int): Contact {
        return getItem(position)
    }

    inner class ContactHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.name
        var textViewTelefono: TextView = itemView.phone
        var textViewEmail: TextView = itemView.Mail
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}