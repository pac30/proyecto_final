package com.example.ud.biblioteca.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.repository.BibliotecaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BibliotecaViewModel : ViewModel() {

    private val repo = BibliotecaRepository()

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    fun cargarLibros() {
        viewModelScope.launch {
            _libros.value = repo.obtenerLibros()
        }
    }

    suspend fun comprarLibro(libro: Libro, cantidad: Int) {
        repo.registrarCompra(libro, cantidad)
        cargarLibros() // recargar datos actualizados
    }

    suspend fun venderLibro(libro: Libro, cantidad: Int) {
        repo.registrarVenta(libro, cantidad)
        cargarLibros() // recargar datos actualizados
    }

}
