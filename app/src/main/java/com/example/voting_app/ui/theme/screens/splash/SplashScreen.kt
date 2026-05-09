package com.example.voting_app.ui.theme.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voting_app.ui.theme.VotingappTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds splash delay

        // TODO: Replace with real auth check
        val isLoggedIn = false

        if (isLoggedIn) {
            onNavigateToDashboard()
        } else {
            onNavigateToLogin()
        }
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Voting App",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashPreview() {
    VotingappTheme {
        SplashContent()
    }
}
