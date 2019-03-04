package com.example.diego.appcontactos7

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_contact.*
import java.io.IOException
class CreateContact : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 2
    val REQUEST_GALLERY = 1

    //Se obtiene la información
    companion object {
        const val EXTRA_ID = "com.example.diego.appcontactos7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.diego.appcontactos7.EXTRA_NOMBRE"
        const val EXTRA_PHONE = "com.example.diego.appcontactos7.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.diego.appcontactos7.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.diego.appcontactos7.EXTRA_PRIORITY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.adduser)

        //EN caso de que desee editar
        if(intent.getIntExtra(EXTRA_ID,-1) != -1 ) {
            nombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
            telefono.setText(intent.getStringExtra(EXTRA_PHONE))
            correo.setText(intent.getStringExtra(EXTRA_MAIL))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        }else{
        }
        //En caso de que desee crear

        //Boton de guardar
        save.setOnClickListener{
            guardarContacto()
        }

        //Boton de regresar
        back.setOnClickListener{
            val intento = Intent(this, MainActivity::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }
    }




    private fun guardarContacto(){
        if(nombre.text.toString().isNotEmpty() && telefono.text.toString().isNotEmpty() || correo.text.toString().isNotEmpty()){

            val data = Intent().apply {
                putExtra(EXTRA_NOMBRE,nombre.text.toString())
                putExtra(EXTRA_PHONE,telefono.text.toString())
                putExtra(EXTRA_MAIL,correo.text.toString())
                putExtra(EXTRA_PRIORITY,number_picker_priority.value)
                if (intent.getIntExtra(EXTRA_ID,-1) != -1){
                    putExtra(EXTRA_ID,intent.getIntExtra(EXTRA_ID,-1))
                }
            }
            setResult(Activity.RESULT_OK,data)
            finish()

        }else{
            Toast.makeText(this,"No se pudo, asegurate de llenar todos los espacios", Toast.LENGTH_SHORT).show()
            return
        }
    }
}