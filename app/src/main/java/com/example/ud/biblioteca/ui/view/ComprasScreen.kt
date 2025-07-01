package com.example.ud.biblioteca.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    viewModel: BibliotecaViewModel = viewModel(),
    navController: NavController
) {
    val libros by viewModel.libros.collectAsState()
    var cantidad by remember { mutableStateOf("") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }
    val context = LocalContext.current

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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad a comprar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val cant = cantidad.toIntOrNull()
                    if (libroSeleccionado != null && cant != null && cant > 0) {
                        viewModel.viewModelScope.launch {
                            viewModel.comprarLibro(libroSeleccionado!!, cant)
                            Toast.makeText(context, "Compra registrada", Toast.LENGTH_SHORT).show()
                            cantidad = ""
                            libroSeleccionado = null
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Compra")
            }
        }
    }
}