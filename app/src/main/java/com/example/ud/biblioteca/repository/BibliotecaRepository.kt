package com.example.ud.biblioteca.repository

import com.example.ud.biblioteca.model.Libro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class BibliotecaRepository {
    private val db = FirebaseDatabase.getInstance()
    private val librosRef = db.getReference("libros")
    private val comprasRef = db.getReference("compras")
    private val ventasRef = db.getReference("ventas")

    suspend fun obtenerLibros(): List<Libro> {
        return try {
            val snapshot = librosRef.get().await()
            snapshot.children.mapNotNull {
                val libro = it.getValue(Libro::class.java)
                libro?.copy(id = it.key ?: "")
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun registrarCompra(libro: Libro, cantidadComprada: Int) {
        val nuevaCantidad = libro.cantidad + cantidadComprada
        librosRef.child(libro.id).child("cantidad").setValue(nuevaCantidad).await()

        val compraId = comprasRef.push().key ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "desconocido"

        val compraData = mapOf(
            "libroId" to libro.id,
            "titulo" to libro.titulo,
            "cantidad" to cantidadComprada,
            "usuarioId" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        comprasRef.child(compraId).setValue(compraData).await()
    }

    suspend fun registrarVenta(libro: Libro, cantidadVendida: Int) {
        val nuevaCantidad = (libro.cantidad - cantidadVendida).coerceAtLeast(0)
        librosRef.child(libro.id).child("cantidad").setValue(nuevaCantidad).await()

        val ventaId = ventasRef.push().key ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "desconocido"

        val ventaData = mapOf(
            "libroId" to libro.id,
            "titulo" to libro.titulo,
            "cantidad" to cantidadVendida,
            "usuarioId" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        ventasRef.child(ventaId).setValue(ventaData).await()
    }
}