package com.example.ud.biblioteca.model

data class Libro(
    val id: String = "", // ID del libro (Firebase key)
    val titulo: String = "",
    val autor: String = "",
    val categoria: String = "",
    val cantidad: Int = 0,
    val disponible: Boolean = true
)