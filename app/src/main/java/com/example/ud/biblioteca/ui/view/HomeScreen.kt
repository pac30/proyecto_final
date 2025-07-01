package com.example.ud.biblioteca.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ud.biblioteca.ui.util.Strings
import com.example.ud.biblioteca.ui.viewmodel.LanguageViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    languageViewModel: LanguageViewModel = viewModel()
) {
    val lang by languageViewModel.currentLanguage

    Scaffold(
        topBar = { TopAppBar(title = { Text(Strings.get("home_title", lang)) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Strings.get("welcome", lang),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("inventario") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("inventory", lang))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("compras") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("buy", lang))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("ventas") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("sell", lang))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("finanzas") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("finance", lang))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { languageViewModel.toggleLanguage() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("change_lang", lang))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(Strings.get("logout", lang), color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}