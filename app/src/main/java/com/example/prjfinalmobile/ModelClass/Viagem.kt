package com.example.prjfinalmobile.ModelClass

import java.time.LocalDate
import java.util.Date


enum class TipoViagem(){
    Leisure,
    Business,
}

class Viagem(
    val id: Long = 1,
    val destination: String = "",
    val type: TipoViagem = TipoViagem.Leisure,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val budget: Float? = null
)