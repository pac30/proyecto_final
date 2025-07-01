package com.example.ud.biblioteca.ui.view

import androidx.compose.foundation.layout.*
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
fun ResumenFinanzasScreen(
    navController: NavController,
    viewModel: BibliotecaViewModel = viewModel()
) {
    val lang = LocalLanguage.current.value
    val totalComprado by viewModel.totalComprado.collectAsState()
    val totalVendido by viewModel.totalVendido.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarFinanzas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("finance", lang)) },
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = Strings.get("total_spent", lang),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$${"%.2f".format(totalComprado)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = Strings.get("total_earned", lang),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$${"%.2f".format(totalVendido)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = Strings.get("balance", lang),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$${"%.2f".format(totalVendido - totalComprado)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
