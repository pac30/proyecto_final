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
import com.example.ud.biblioteca.ui.util.LocalLanguage
import com.example.ud.biblioteca.ui.util.Strings
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    navController: NavController,
    viewModel: BibliotecaViewModel = viewModel()
) {
    val lang = LocalLanguage.current.value

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
                title = { Text(Strings.get("buy", lang)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Text(Strings.get("select_book", lang))

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
                Text(Strings.get("book_details", lang))
                Text("${Strings.get("author", lang)}: ${libro.autor}")
                Text("${Strings.get("category", lang)}: ${libro.categoria}")
                Text("${Strings.get("available", lang)}: ${if (libro.disponible) Strings.get("yes", lang) else Strings.get("no", lang)}")
                Text("${Strings.get("quantity", lang)}: ${libro.cantidad}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text(Strings.get("purchase_quantity", lang)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text(Strings.get("purchase_price", lang)) },
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
                            Toast.makeText(context, Strings.get("purchase_registered", lang), Toast.LENGTH_SHORT).show()
                            cantidad = ""
                            precio = ""
                            libroSeleccionado = null
                        }
                    } else {
                        Toast.makeText(context, Strings.get("invalid_data", lang), Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("buy", lang))
            }
        }
    }
}