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
    val message by viewModel.uiMessage.collectAsState()
    val context = LocalContext.current

    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }

    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, Strings.get(it, lang), Toast.LENGTH_SHORT).show()
            if (it == "purchase_registered") {
                cantidad = ""
                precio = ""
                libroSeleccionado = null
            }
            viewModel.clearUiMessage()
        }
    }

    // (Mantén el resto de tu UI igual, excepto el botón):
    Button(
        onClick = {
            viewModel.comprar(libroSeleccionado, cantidad, precio)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(Strings.get("buy", lang))
    }
}
