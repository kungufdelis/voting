package com.example.voting_app.ui.theme.screens.votelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.voting_app.navigation.ROUTE_VOTE_DETAIL


@Composable
fun VoteListScreen(navController: NavHostController) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Available Votes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        listOf("President Election", "Student Leader", "Class Representative").forEachIndexed { index, title ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("$ROUTE_VOTE_DETAIL/${index + 1}")
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Tap to vote", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}