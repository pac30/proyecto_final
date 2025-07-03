package com.example.ud.biblioteca.ui.view

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.ud.biblioteca.ui.util.NotificationUtils
import com.example.ud.biblioteca.ui.util.Strings
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    navController: NavController,
    viewModel: BibliotecaViewModel = viewModel()
) {
    val lang = LocalLanguage.current.value
    val libros by viewModel.libros.collectAsState()
    val message by viewModel.uiMessage.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }
    var usarNuevoLibro by remember { mutableStateOf(false) }
    var nuevoTitulo by remember { mutableStateOf("") }
    var nuevoAutor by remember { mutableStateOf("") }
    var nuevaCategoria by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(message) {
        message?.let {
            if (it == "purchase_registered") {
                val titulo = if (usarNuevoLibro) nuevoTitulo else libroSeleccionado?.titulo ?: ""
                val autor = if (usarNuevoLibro) nuevoAutor else libroSeleccionado?.autor ?: ""
                val categoria = if (usarNuevoLibro) nuevaCategoria else libroSeleccionado?.categoria ?: ""
                val cantidadInt = cantidad.toIntOrNull() ?: 0
                val precioDouble = precio.toDoubleOrNull() ?: 0.0

                val detalle = """
                    Título: $titulo
                    Autor: $autor
                    Categoría: $categoria
                    Cantidad: $cantidadInt
                    Precio: $${"%.2f".format(precioDouble)}
                """.trimIndent()

                NotificationUtils.showCompraNotification(context, detalle)

                cantidad = ""
                precio = ""
                nuevoTitulo = ""
                nuevoAutor = ""
                nuevaCategoria = ""
                libroSeleccionado = null
                usarNuevoLibro = false
            }

            Toast.makeText(context, Strings.get(it, lang), Toast.LENGTH_SHORT).show()
            viewModel.clearUiMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("buy_books", lang)) },
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
            Text(Strings.get("select_book", lang))

            libros.forEach { libro ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = libro == libroSeleccionado && !usarNuevoLibro,
                        onClick = {
                            libroSeleccionado = libro
                            usarNuevoLibro = false
                        }
                    )
                    Text("${libro.titulo} (${libro.cantidad})")
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = usarNuevoLibro,
                    onClick = {
                        libroSeleccionado = null
                        usarNuevoLibro = true
                    }
                )
                Text("Nuevo libro")
            }

            if (usarNuevoLibro) {
                OutlinedTextField(
                    value = nuevoTitulo,
                    onValueChange = { nuevoTitulo = it },
                    label = { Text("Título del nuevo libro") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nuevoAutor,
                    onValueChange = { nuevoAutor = it },
                    label = { Text("Autor del nuevo libro") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nuevaCategoria,
                    onValueChange = { nuevaCategoria = it },
                    label = { Text("Categoría del nuevo libro") },
                    modifier = Modifier.fillMaxWidth()
                )
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
                    if (usarNuevoLibro && nuevoTitulo.isNotBlank()) {
                        viewModel.comprarNuevoLibro(
                            titulo = nuevoTitulo.trim(),
                            autor = nuevoAutor.trim(),
                            categoria = nuevaCategoria.trim(),
                            cantidadStr = cantidad,
                            precioStr = precio
                        )
                    } else {
                        viewModel.comprar(libroSeleccionado, cantidad, precio)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("buy", lang))
            }
        }
    }
}
