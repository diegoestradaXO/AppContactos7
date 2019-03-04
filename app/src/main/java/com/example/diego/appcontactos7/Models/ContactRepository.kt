package com.example.diego.appcontactos7.Models

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask

//Modelo de Contacto para Repositorio

class ContactRepository(application: Application){
    var ContactDao: ContactDAO
    //Lista de todos los contactos como un LiveData

    private var myContacts: LiveData<List<Contact>>

    init{
        val database: ContactDB = ContactDB.getInstance(
            application.applicationContext
        )!!
        ContactDao = database.contactDao()
        myContacts = ContactDao.getAllContacts()
    }

    //Función de inserción a la base de datos
    fun insert(contact: Contact){
        val insertContactAsyncTask = InsertContactAsyncTask(ContactDao).execute(contact)
    }

    //Actualizar
    fun update(contact: Contact){
        val updateContactAsyncTask = UpdateContactAsyncTask(ContactDao).execute(contact)
    }

    //Borrar
    fun delete(contact: Contact){
        val deleteContactAsyncTask = DeleteContactAsyncTask(ContactDao).execute(contact)
    }

    fun deleteAllContacts(){
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(ContactDao).execute()
    }

    //Funcion para obtener todos los contactos del Live  Daa
    fun getAllContacts(): LiveData<List<Contact>> {
        return myContacts
    }

    companion object {
        private class InsertContactAsyncTask(val contactDao: ContactDAO) : AsyncTask<Contact, Unit, Unit>(){
            override fun doInBackground(vararg p0: Contact?){
                contactDao.insert(p0[0]!!)
            }
        }
        private class UpdateContactAsyncTask(val contactDao: ContactDAO) : AsyncTask<Contact, Unit, Unit>(){
            override fun doInBackground(vararg p0: Contact?){
                contactDao.update(p0[0]!!)
            }
        }
        private class DeleteContactAsyncTask(val contactDao: ContactDAO): AsyncTask<Contact, Unit, Unit>() {
            override fun doInBackground(vararg p0: Contact?) {
                contactDao.delete(p0[0]!!)
            }
        }
        private class DeleteAllNotesAsyncTask(val contactDao: ContactDAO): AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                contactDao.deleteAllContacts()
            }
        }
    }
}