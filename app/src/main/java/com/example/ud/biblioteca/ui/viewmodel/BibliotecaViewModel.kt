package com.example.ud.biblioteca.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.repository.BibliotecaRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BibliotecaViewModel : ViewModel() {

    private val repo = BibliotecaRepository()

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    private val _totalComprado = MutableStateFlow(0.0)
    val totalComprado: StateFlow<Double> = _totalComprado

    private val _totalVendido = MutableStateFlow(0.0)
    val totalVendido: StateFlow<Double> = _totalVendido

    fun cargarLibros() {
        viewModelScope.launch {
            _libros.value = repo.obtenerLibros()
        }
    }

    fun cargarFinanzas() {
        viewModelScope.launch {
            val ref = FirebaseDatabase.getInstance().getReference("finanzas")
            val compradoSnapshot = ref.child("totalComprado").get().await()
            val vendidoSnapshot = ref.child("totalVendido").get().await()

            _totalComprado.value = compradoSnapshot.getValue(Double::class.java) ?: 0.0
            _totalVendido.value = vendidoSnapshot.getValue(Double::class.java) ?: 0.0
        }
    }

    suspend fun comprarLibro(libro: Libro, cantidad: Int, precio: Double) {
        repo.registrarCompra(libro, cantidad, precio)
        cargarLibros()
    }

    suspend fun venderLibro(libro: Libro, cantidad: Int, precio: Double) {
        repo.registrarVenta(libro, cantidad, precio)
        cargarLibros()
    }
}