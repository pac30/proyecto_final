package com.example.ud.biblioteca.ui.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalLanguage = compositionLocalOf { mutableStateOf("es") }

object Strings {
    private val texts = mapOf(
        "es" to mapOf(
            "home_title" to "Gestor de Biblioteca",
            "welcome" to "Bienvenido a la Biblioteca",
            "inventory" to "Ver Inventario",
            "buy" to "Registrar Compra",
            "sell" to "Registrar Venta",
            "finance" to "Resumen Financiero",
            "logout" to "Cerrar sesión",
            "change_lang" to "Cambiar idioma",
            "quantity" to "Cantidad",
            "available" to "Disponible"
        ),
        "en" to mapOf(
            "home_title" to "Library Manager",
            "welcome" to "Welcome to the Library",
            "inventory" to "View Inventory",
            "buy" to "Register Purchase",
            "sell" to "Register Sale",
            "finance" to "Financial Summary",
            "logout" to "Sign out",
            "change_lang" to "Change Language",
            "quantity" to "Quantity",
            "available" to "Available"
        )
    )

    fun get(key: String, lang: String): String {
        return texts[lang]?.get(key) ?: key
    }
}