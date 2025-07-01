package com.example.ud.biblioteca.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ud.biblioteca.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState: StateFlow<Result<Unit>?> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = repository.login(email, password)
        }
    }

    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()
}
