package com.example.diego.appcontactos7.Models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDAO{
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY priority DESC")
    fun getAllContacts(): LiveData<List<Contact>>

}