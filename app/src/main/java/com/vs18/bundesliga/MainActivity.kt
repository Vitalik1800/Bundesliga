package com.vs18.bundesliga

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.*
import com.vs18.bundesliga.datastore.AuthPreferences
import com.vs18.bundesliga.repository.auth.AuthRepository
import com.vs18.bundesliga.ui.auth.AuthScreen
import com.vs18.bundesliga.ui.bundesliga.BundesligaScreen
import com.vs18.bundesliga.ui.theme.BundesligaTheme
import com.vs18.bundesliga.viewmodel.FootballViewModel
import com.vs18.bundesliga.viewmodel.auth.AuthViewModel
import com.vs18.bundesliga.viewmodel.auth.AuthViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: FootballViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val prefs = remember { AuthPreferences(context) }

            val token by prefs.token.collectAsState(initial = null)
            val authorized  = token != null

            BundesligaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    if (authorized) {
                        BundesligaScreen(viewModel)
                    }
                    else {
                        AuthScreen(
                            onAuthorized = {}
                        )
                    }
                }
            }
        }
    }
}