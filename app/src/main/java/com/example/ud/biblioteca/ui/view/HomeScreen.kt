package com.example.ud.biblioteca.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.ud.biblioteca.ui.util.LocalLanguage
import com.example.ud.biblioteca.ui.util.Strings
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val langState = LocalLanguage.current
    val lang = langState.value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(Strings.get("home_title", lang)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = Strings.get("welcome", lang),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🖼 Imagen decorativa
            Image(
                painter = rememberAsyncImagePainter("https://www.oficinasmontiel.com/blog/wp-content/uploads/2024/05/96-2048x1365.jpg"),
                contentDescription = "Imagen de biblioteca",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
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
                onClick = {
                    langState.value = if (lang == "es") "en" else "es"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.get("change_lang", lang))
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                Text(
                    Strings.get("logout", lang),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}