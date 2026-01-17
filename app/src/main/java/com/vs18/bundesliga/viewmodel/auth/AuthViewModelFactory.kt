package com.vs18.bundesliga.viewmodel.auth

import androidx.lifecycle.*
import com.vs18.bundesliga.datastore.AuthPreferences
import com.vs18.bundesliga.repository.auth.AuthRepository

class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val prefs: AuthPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository, prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
