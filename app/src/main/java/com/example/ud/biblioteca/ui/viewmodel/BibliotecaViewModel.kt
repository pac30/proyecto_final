package com.example.ud.biblioteca.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.repository.BibliotecaRepository
import com.example.ud.biblioteca.ui.util.NotificationUtils
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BibliotecaViewModel(
    private val repo: BibliotecaRepository = BibliotecaRepository()
) : ViewModel() {

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    private val _totalComprado = MutableStateFlow(0.0)
    val totalComprado: StateFlow<Double> = _totalComprado

    private val _totalVendido = MutableStateFlow(0.0)
    val totalVendido: StateFlow<Double> = _totalVendido

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage

    fun clearUiMessage() {
        _uiMessage.value = null
    }

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

    fun comprar(libro: Libro?, cantidadStr: String, precioStr: String, context: Context? = null) {
        viewModelScope.launch {
            val cant = cantidadStr.toIntOrNull()
            val precio = precioStr.toDoubleOrNull()

            if (libro == null || cant == null || precio == null || cant <= 0 || precio <= 0.0) {
                _uiMessage.value = "invalid_data"
                return@launch
            }

            repo.registrarCompra(libro, cant, precio)
            cargarLibros()
            _uiMessage.value = "purchase_registered"

            context?.let {
                NotificationUtils.showCompraNotification(it, libro.titulo)
            }
        }
    }

    fun vender(libro: Libro?, cantidadStr: String, precioStr: String) {
        viewModelScope.launch {
            val cant = cantidadStr.toIntOrNull()
            val precio = precioStr.toDoubleOrNull()

            if (libro == null || cant == null || precio == null || cant <= 0 || precio <= 0.0 || cant > libro.cantidad) {
                _uiMessage.value = "invalid_data"
                return@launch
            }

            repo.registrarVenta(libro, cant, precio)
            cargarLibros()
            _uiMessage.value = "sale_registered"
        }
    }
    fun comprarNuevoLibro(titulo: String, autor: String, categoria: String, cantidadStr: String, precioStr: String) {
        viewModelScope.launch {
            val cant = cantidadStr.toIntOrNull()
            val precio = precioStr.toDoubleOrNull()

            if (titulo.isBlank() || autor.isBlank() || categoria.isBlank() || cant == null || precio == null || cant <= 0 || precio <= 0.0) {
                _uiMessage.value = "invalid_data"
                return@launch
            }

            val nuevoLibro = Libro(
                titulo = titulo,
                autor = autor,
                categoria = categoria,
                cantidad = 0
            )

            repo.agregarNuevoLibro(nuevoLibro, cant, precio)
            cargarLibros()
            _uiMessage.value = "purchase_registered"
        }
    }

}
