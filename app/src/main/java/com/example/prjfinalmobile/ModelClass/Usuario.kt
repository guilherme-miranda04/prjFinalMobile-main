package com.example.prjfinalmobile.ModelClass

import androidx.room.PrimaryKey

data class Usuario (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val login: String = "",
    val senha: String = "",
    val email : String = ""
)