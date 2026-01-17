package com.vs18.bundesliga.model.auth

data class AuthRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)