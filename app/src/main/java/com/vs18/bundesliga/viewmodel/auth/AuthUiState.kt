package com.vs18.bundesliga.viewmodel.auth

sealed class AuthUiState {
    object Idle: AuthUiState()
    object Loading: AuthUiState()
    object Authorized: AuthUiState()
    data class Message(val text: String) : AuthUiState()
    data class Error(val text: String) : AuthUiState()
}