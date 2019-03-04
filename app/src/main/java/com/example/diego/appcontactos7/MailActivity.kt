package com.example.diego.appcontactos7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_mail.*

class MailActivity : AppCompatActivity() {
    //Initial strings
    lateinit var sender: String
    lateinit var receiver: String
    lateinit var message: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
        }

        //Button for send message
        send.setOnClickListener {
            sender = recipent.text.toString()
            receiver = mailto.text.toString()
            message = text.text.toString()

            Snackbar.make(
                parent_view,
                "$message enviado desde $sender hacia $receiver",
                Snackbar.LENGTH_LONG
            ).show()
        }

        home1.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, MainActivity::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }
    }

    private fun handleSendText(intent: Intent) {

        message = intent.getStringExtra(Intent.EXTRA_TEXT)
        receiver = intent.getStringExtra(Intent.EXTRA_EMAIL)

        text.setText(message)
        mailto.setText(receiver)
    }
}