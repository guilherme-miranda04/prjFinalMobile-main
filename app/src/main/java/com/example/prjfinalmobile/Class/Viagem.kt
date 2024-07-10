package com.example.prjfinalmobile.Class

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class TipoViagem() {
    Leisure,
    Business
}
@Entity
data class Viagem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val destination: String = "",
    val type: TipoViagem = TipoViagem.Leisure,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val budget: Float? = null
    ){
}