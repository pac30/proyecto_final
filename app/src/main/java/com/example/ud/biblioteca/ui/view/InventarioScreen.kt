package com.example.ud.biblioteca.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen(
    viewModel: BibliotecaViewModel = viewModel(),
    navController: NavController
) {
    val libros by viewModel.libros.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(libros) { libro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = libro.titulo, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Autor: ${libro.autor}")
                        Text(text = "Categoría: ${libro.categoria}")
                        Text(text = "Cantidad: ${libro.cantidad}")
                        Text(text = if (libro.disponible) "Disponible" else "No disponible")
                    }
                }
            }
        }
    }
}
