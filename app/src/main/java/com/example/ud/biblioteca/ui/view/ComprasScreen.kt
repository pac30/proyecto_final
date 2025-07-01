package com.example.ud.biblioteca.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    navController: NavController,
    viewModel: BibliotecaViewModel = viewModel()
) {
    val libros by viewModel.libros.collectAsState()
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Seleccione un libro:")

            libros.forEach { libro ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = libro == libroSeleccionado,
                        onClick = { libroSeleccionado = libro }
                    )
                    Text("${libro.titulo} (${libro.cantidad})")
                }
            }

            libroSeleccionado?.let { libro ->
                Spacer(modifier = Modifier.height(16.dp))
                Text("Detalles del libro:")
                Text("Autor: ${libro.autor}")
                Text("Categoría: ${libro.categoria}")
                Text("Disponible: ${if (libro.disponible) "Sí" else "No"}")
                Text("Cantidad en inventario: ${libro.cantidad}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad a comprar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio de compra por unidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val cant = cantidad.toIntOrNull()
                    val pr = precio.toDoubleOrNull()
                    if (libroSeleccionado != null && cant != null && cant > 0 &&
                        pr != null && pr > 0.0
                    ) {
                        coroutineScope.launch {
                            viewModel.comprarLibro(libroSeleccionado!!, cant, pr)
                            Toast.makeText(context, "Compra registrada", Toast.LENGTH_SHORT).show()
                            cantidad = ""
                            precio = ""
                            libroSeleccionado = null
                        }
                    } else {
                        Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Compra")
            }
        }
    }
}
