package com.example.ud.biblioteca

import com.example.ud.biblioteca.model.Libro
import com.example.ud.biblioteca.repository.BibliotecaRepository
import com.example.ud.biblioteca.ui.viewmodel.BibliotecaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class BibliotecaViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repo: BibliotecaRepository
    private lateinit var viewModel: BibliotecaViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mock()
        viewModel = BibliotecaViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `compra con datos válidos actualiza libros y mensaje`() = runTest {
        val libro = Libro("1", "Título", "Autor", "Categoría", 10, true)
        val librosActualizados = listOf(libro)

        // Simula que después de registrar la compra, cargarLibros() devuelve esta lista
        whenever(repo.obtenerLibros()).thenReturn(librosActualizados)

        // Ejecuta
        viewModel.comprar(libro, "2", "15.0")
        advanceUntilIdle()

        // Verifica que se llamaron las funciones esperadas
        verify(repo).registrarCompra(libro, 2, 15.0)
        verify(repo).obtenerLibros()
        assertEquals("purchase_registered", viewModel.uiMessage.value)
        assertEquals(librosActualizados, viewModel.libros.value)
    }
    @Test
    fun `compra con datos inválidos no actualiza libros y muestra error`() = runTest {
        val libro = Libro("1", "Título", "Autor", "Categoría", 10, true)

        // Casos inválidos: cantidad vacía, precio negativo, libro null
        viewModel.comprar(null, "2", "10.0")
        advanceUntilIdle()
        assertEquals("invalid_data", viewModel.uiMessage.value)

        viewModel.comprar(libro, "0", "10.0")
        advanceUntilIdle()
        assertEquals("invalid_data", viewModel.uiMessage.value)

        viewModel.comprar(libro, "2", "-5.0")
        advanceUntilIdle()
        assertEquals("invalid_data", viewModel.uiMessage.value)

        // Verifica que no se llamó al repositorio en ninguno
        verify(repo, never()).registrarCompra(any(), any(), any())
    }
    @Test
    fun `venta con cantidad válida actualiza libros y mensaje`() = runTest {
        val libro = Libro("1", "Título", "Autor", "Categoría", 10, true)
        val librosActualizados = listOf(libro)

        whenever(repo.obtenerLibros()).thenReturn(librosActualizados)

        viewModel.vender(libro, "3", "20.0")
        advanceUntilIdle()

        verify(repo).registrarVenta(libro, 3, 20.0)
        verify(repo).obtenerLibros()
        assertEquals("sale_registered", viewModel.uiMessage.value)
        assertEquals(librosActualizados, viewModel.libros.value)
    }

}
