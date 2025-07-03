package com.example.ud.biblioteca.ui.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalLanguage = compositionLocalOf { mutableStateOf("es") }

object Strings {
    private val texts = mapOf(
        "es" to mapOf(
            // Pantalla principal
            "home_title" to "Gestor de Biblioteca",
            "welcome" to "Bienvenido a la Biblioteca",
            "inventory" to "Ver Inventario",
            "buy" to "Registrar Compra",
            "sell" to "Registrar Venta",
            "finance" to "Resumen Financiero",
            "logout" to "Cerrar sesión",
            "change_lang" to "Cambiar idioma",
            "back" to "Volver",
            "buy_books" to "Comprar libros",

            // Generales
            "quantity" to "Cantidad",
            "available" to "Disponible",
            "yes" to "Sí",
            "no" to "No",
            "invalid_data" to "Datos inválidos",

            // Libros
            "select_book" to "Seleccione un libro:",
            "book_details" to "Detalles del libro:",
            "author" to "Autor",
            "category" to "Categoría",

            // Compras
            "purchase_quantity" to "Cantidad a comprar",
            "purchase_price" to "Precio de compra por unidad",
            "purchase_registered" to "Compra registrada",

            //Inventario
            "title" to "Título",

            //resumenfinanzas
            "total_spent" to "Total invertido en compras:",
            "total_earned" to "Total ganado por ventas:",
            "balance" to "Balance actual",

            //login
            "login_title" to "Iniciar sesión",
            "email" to "Correo electrónico",
            "password" to "Contraseña",
            "login_button" to "Entrar",
            "login_success" to "Inicio de sesión exitoso",
            "login_error" to "Error al iniciar sesión",

            // Ventas
            "sale_quantity" to "Cantidad a vender",
            "sale_price" to "Precio de venta por unidad",
            "sale_registered" to "Venta registrada"
        ),
        "en" to mapOf(
            // Main screen
            "home_title" to "Library Manager",
            "welcome" to "Welcome to the Library",
            "inventory" to "View Inventory",
            "buy" to "Register Purchase",
            "sell" to "Register Sale",
            "finance" to "Financial Summary",
            "logout" to "Sign out",
            "change_lang" to "Change Language",
            "back" to "Back",
            "buy_books" to "Buy Books",

            // General
            "quantity" to "Quantity",
            "available" to "Available",
            "yes" to "Yes",
            "no" to "No",
            "invalid_data" to "Invalid data",

            // Books
            "select_book" to "Select a book:",
            "book_details" to "Book details:",
            "author" to "Author",
            "category" to "Category",

            // Purchases
            "purchase_quantity" to "Purchase quantity",
            "purchase_price" to "Purchase price per unit",
            "purchase_registered" to "Purchase registered",

            //inventary
            "title" to "Title",

            //resemefinace
            "total_spent" to "Total spent on purchases:",
            "total_earned" to "Total earned from sales:",
            "balance" to "Current balance",

            //login
            "login_title" to "Log In",
            "email" to "Email",
            "password" to "Password",
            "login_button" to "Sign In",
            "login_success" to "Login successful",
            "login_error" to "Login failed",

            // Sales
            "sale_quantity" to "Sale quantity",
            "sale_price" to "Sale price per unit",
            "sale_registered" to "Sale registered"
        )
    )

    fun get(key: String, lang: String): String {
        return texts[lang]?.get(key) ?: key
    }
}