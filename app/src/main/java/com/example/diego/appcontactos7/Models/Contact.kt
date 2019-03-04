package com.example.diego.appcontactos7.Models


import androidx.room.Entity
import androidx.room.PrimaryKey

//Modelo para el contacto
@Entity(tableName = "contact_table")
data class Contact(var name:String, var phone:String, var email:String, var priority: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


}