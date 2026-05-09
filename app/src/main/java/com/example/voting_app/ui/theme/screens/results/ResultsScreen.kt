package com.example.voting_app.ui.theme.screens.results

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.voting_app.navigation.ROUTE_DASHBOARD

@Composable
fun ResultsScreen(
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
                    text = "Results",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Vote ID: $voteId")
                Text("Candidate A: 60%")
                Text("Candidate B: 40%")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    navController.navigate(ROUTE_DASHBOARD) {
                        popUpTo(ROUTE_DASHBOARD) { inclusive = true }
                    }
                }) {
                    Text("Back to Dashboard")
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultsPreview() {
    ResultsScreen(
        navController = NavHostController(LocalContext.current),
        voteId = "12345"
    )



}