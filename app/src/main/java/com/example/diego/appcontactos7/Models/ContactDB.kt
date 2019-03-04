package com.example.diego.appcontactos7.Models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.diego.appcontactos7.R
import kotlinx.android.synthetic.main.activity_create_contact.*
import java.io.ByteArrayOutputStream

//Modelo del contacto para la Base de Datos, ROOM

@Database(entities = [Contact::class], version=2,exportSchema = false)
abstract class ContactDB : androidx.room.RoomDatabase(){

    abstract fun contactDao(): ContactDAO

    companion object {
        private var instance: ContactDB? = null//Atributo de instancia

        //Singleton, si ya no existe la instancia, la crea, pero si ya existe, la retorna.
        fun getInstance(context: Context): ContactDB? {
            if (instance == null) {
                synchronized(ContactDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDB::class.java, "contacts_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build()
                }
            }
            return instance
        }

        //Hace la instancia vacía
        fun destroyInstace(){
            instance = null
        }

        private val roomCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }


    class PopulateDbAsyncTask(db: ContactDB?):AsyncTask<Unit, Unit, Unit>(){
        private val ContactDao = db?.contactDao()

        override fun doInBackground(vararg params: Unit?) {
        }

        fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            return stream.toByteArray()
        }
    }
}