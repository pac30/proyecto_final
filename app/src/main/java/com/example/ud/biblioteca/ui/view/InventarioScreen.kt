package com.example.ud.biblioteca.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ud.biblioteca.ui.util.LocalLanguage
import com.example.ud.biblioteca.ui.util.Strings
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen(
    navController: NavController,
    viewModel: BibliotecaViewModel = viewModel()
) {
    val lang = LocalLanguage.current.value
    val libros by viewModel.libros.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("inventory", lang)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Strings.get("back", lang))
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
            libros.forEach { libro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${Strings.get("title", lang)}: ${libro.titulo}")
                        Text("${Strings.get("author", lang)}: ${libro.autor}")
                        Text("${Strings.get("category", lang)}: ${libro.categoria}")
                        Text("${Strings.get("quantity", lang)}: ${libro.cantidad}")
                        Text("${Strings.get("available", lang)}: ${if (libro.disponible) Strings.get("yes", lang) else Strings.get("no", lang)}")
                    }
                }
            }
        }
    }
}
