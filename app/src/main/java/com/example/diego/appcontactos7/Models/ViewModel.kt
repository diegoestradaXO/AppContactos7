package com.example.diego.appcontactos7.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class ViewModel(application: android.app.Application): AndroidViewModel(application){
    private var repository : ContactRepository = ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact){
        repository.insert(contact)
    }

    fun update(contact: Contact){
        repository.update((contact))
    }

    fun delete(contact: Contact){
        repository.delete(contact)
    }

    fun deleteAllContacts(){
        repository.deleteAllContacts()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}