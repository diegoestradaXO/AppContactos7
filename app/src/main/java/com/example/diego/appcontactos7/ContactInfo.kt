package com.example.diego.appcontactos7

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import com.example.diego.appcontactos7.Models.ContactAdapter
import com.example.diego.appcontactos7.R.id.edit_query
import com.example.diego.appcontactos7.R.id.parent_view
import com.example.diego.appcontactos7.Models.Contact
import com.example.diego.appcontactos7.Models.ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_contact_info.*
import kotlinx.android.synthetic.main.activity_mail.*
import kotlinx.android.synthetic.main.activity_create_contact.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.*

//Activity que muestra la información del contacto creado, en esta actividad es posible llamar al contacto,
//mandarle un mail, y editar los datos.

class ContactInfo : AppCompatActivity() {

    private var Id:Int = -1
    private lateinit var contactViewModel: ViewModel

    companion object {
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)

        //Se obtienen los datos del contacto mediante los putExtra del activivty Main
        //Luego se muestran en el layout

        val nombre = intent.getStringExtra(CreateContact.EXTRA_NOMBRE)
        showNombre.setText(nombre)
        val number = intent.getStringExtra(CreateContact.EXTRA_PHONE)
        showPhone.setText(number)
        val mail = intent.getStringExtra(CreateContact.EXTRA_MAIL)
        showMail.setText(mail)

        //Aplica el ViewModel
        contactViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        Id = intent.getIntExtra(CreateContact.EXTRA_ID, 1)


        //Boton de home
        home.setOnClickListener{
            val intento = Intent(baseContext, MainActivity::class.java)
            //Se abre el main
            startActivity(intento)
            this.finish()
        }

        //Boton para editar
        edit.setOnClickListener{
            val intent = Intent(baseContext, CreateContact::class.java)
            intent.putExtra(CreateContact.EXTRA_NOMBRE, nombre)
            intent.putExtra(CreateContact.EXTRA_PHONE, number)
            intent.putExtra(CreateContact.EXTRA_MAIL, mail)
            intent.putExtra(CreateContact.EXTRA_ID, Id)
            intent.putExtra(CreateContact.EXTRA_PRIORITY, intent.getStringExtra(CreateContact.EXTRA_PRIORITY))
            startActivityForResult(intent, MainActivity.EDIT_CONTACT_REQUEST)
        }

        //Boton para enviar correo
        sendMail.setOnClickListener{//enviamos a correo
            val emailIntent = Intent(Intent.ACTION_SEND)
            //Making the intent for email
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getIntent().getStringExtra("mail"))
            val nombre = getIntent().getStringExtra(("nombre"))
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mi nombre es $nombre, y mi teléfono es $number")
            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                Log.i("Enviando correo", "")
            } catch (ex: android.content.ActivityNotFoundException) {
                Snackbar.make(parent_view, "No hay cliente de email...", Snackbar.LENGTH_LONG).show()
            }
        }

        //Boton para llamar
        call.setOnClickListener{
            try {
                val intentcall = Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",number,null))//si nos otorgan los permisos
                startActivity(intentcall)
            }
            catch (e: Exception){
                val intentcall = Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",number,null))//si no nos otorgan los perimosos
                startActivity(intentcall)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainActivity.EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(CreateContact.EXTRA_ID, -1)

            val updateContact = Contact(
                data!!.getStringExtra(CreateContact.EXTRA_NOMBRE),
                data.getStringExtra(CreateContact.EXTRA_PHONE),
                data.getStringExtra(CreateContact.EXTRA_MAIL),
                data.getIntExtra(CreateContact.EXTRA_PRIORITY, 1))

            //Actualizacion del contacto seleccionado
            updateContact.id = id!!
            contactViewModel.update(updateContact)
            showNombre.text = updateContact.name
            showPhone.text = updateContact.phone
            showMail.text = updateContact.email
            updateContact.priority

            Id = updateContact.id

        } else {

        }


    }
}