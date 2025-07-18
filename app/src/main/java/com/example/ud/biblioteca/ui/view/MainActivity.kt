package com.example.ud.biblioteca.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.ud.biblioteca.ui.theme.BibliotecaTheme
import com.example.ud.biblioteca.ui.util.LocalLanguage
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel
import com.example.ud.biblioteca.ui.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado global del idioma
            val language = remember { mutableStateOf("es") }

            // Proveedor global del idioma para toda la app
            CompositionLocalProvider(LocalLanguage provides language) {
                BibliotecaTheme {
                    val navController = rememberNavController()
                    val loginViewModel: LoginViewModel = viewModel()
                    val bibliotecaViewModel: BibliotecaViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("inventario") {
                            InventarioScreen(navController = navController)
                        }
                        composable("compras") {
                            ComprasScreen(navController = navController)
                        }
                        composable("ventas") {
                            VentasScreen(navController = navController)
                        }
                        composable("finanzas") {
                            ResumenFinanzasScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
