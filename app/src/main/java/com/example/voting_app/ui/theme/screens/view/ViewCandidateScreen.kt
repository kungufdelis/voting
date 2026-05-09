package com.example.voting_app.ui.theme.screens.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.voting_app.navigation.ROUTE_UPDATE_CANDIDATE

@Composable
fun ViewCandidateScreen(navController: NavController) {

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Candidates", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        listOf("Alice", "John", "Mary").forEach { candidate ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = candidate, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Tap to manage")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val voteId = ""
                            navController.navigate("$ROUTE_UPDATE_CANDIDATE/$voteId")
                        }
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewCandidateScreenPreview() {
    ViewCandidateScreen(navController = rememberNavController())
}
