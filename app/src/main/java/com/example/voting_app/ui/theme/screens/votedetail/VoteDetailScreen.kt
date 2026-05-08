package com.example.voting_app.ui.theme.screens.votedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.voting_app.navigation.ROUTE_RESULTS


@Composable
fun VoteDetailScreen(
    navController: NavHostController,
    voteId: String
) {

    Column(modifier = Modifier.padding(16.dp)) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Vote Details",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Voting ID: $voteId")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    navController.navigate("$ROUTE_RESULTS/$voteId")
                }) {
                    Text("Submit Vote")
                }
            }
        }
    }
}