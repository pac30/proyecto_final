package com.example.ud.biblioteca.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*

class LanguageViewModel : ViewModel() {
    val currentLanguage = mutableStateOf("es")

    fun toggleLanguage() {
        currentLanguage.value = if (currentLanguage.value == "es") "en" else "es"
    }
}