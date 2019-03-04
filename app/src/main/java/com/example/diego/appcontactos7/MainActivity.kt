package com.example.diego.appcontactos7

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diego.appcontactos7.Models.Contact
import com.example.diego.appcontactos7.Models.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.example.diego.appcontactos7.Models.ViewModel

@Suppress("PLUGIN_WARNING")
class MainActivity : AppCompatActivity() {



    private lateinit var contactViewModel: ViewModel

    companion object {
        lateinit var adapter: ContactAdapter
        //Constants for the intent keys
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_CONTACT_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonAddContact.setOnClickListener{
            startActivityForResult(
                Intent(this, CreateContact::class.java),
                ADD_CONTACT_REQUEST
            )
        }


        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        recycler_view.setHasFixedSize(true)

        contactViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)



        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)
        })



        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeContact(viewHolder.adapterPosition)
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter = ContactAdapter()

        //Se abre la información del contacto
        adapter.setOnItemClickListener(object: ContactAdapter.OnItemClickListener{
            override  fun onItemClick(contact: Contact){
                val intent = Intent(baseContext, ContactInfo::class.java)
                intent.putExtra(CreateContact.EXTRA_NOMBRE,contact.name)
                intent.putExtra(CreateContact.EXTRA_PHONE,contact.phone)
                intent.putExtra(CreateContact.EXTRA_MAIL,contact.email)
                intent.putExtra(CreateContact.EXTRA_ID,contact.id)
                intent.putExtra(CreateContact.EXTRA_PRIORITY,contact.priority)
                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }

        })


        recycler_view.adapter = adapter

        recycler_view.setOnClickListener {

            startActivityForResult(
                Intent(this, ContactInfo::class.java),ADD_CONTACT_REQUEST)
        }
    }



    private fun removeContact(position: Int) {
        //Función para borrar
        val contactRemoved = adapter.getContactAt(position)
        Toast.makeText(this, "Contacto '${contactRemoved.name}' eliminado", Toast.LENGTH_LONG).show()
        contactViewModel.delete(contactRemoved)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newContact = Contact(
                data!!.getStringExtra(CreateContact.EXTRA_NOMBRE),
                data!!.getStringExtra(CreateContact.EXTRA_PHONE),
                data!!.getStringExtra(CreateContact.EXTRA_MAIL),
                data.getIntExtra(CreateContact.EXTRA_PRIORITY, 1))

            contactViewModel.insert(newContact)

            Toast.makeText(this,"Exito", Toast.LENGTH_LONG).show()

        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this,"Exito", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this,"No se ha podido Guardar",Toast.LENGTH_LONG).show()

        }


    }
}