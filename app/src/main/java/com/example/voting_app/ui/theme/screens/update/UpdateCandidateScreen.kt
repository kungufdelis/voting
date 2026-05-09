package com.example.voting_app.ui.theme.screens.update

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.voting_app.ui.theme.VotingappTheme

@Composable
fun UpdateCandidateScreen(
    navController: NavController,
    candidateId: String
) {
    UpdateCandidateContent(
        candidateId = candidateId,
        onUpdateClick = { name, position ->
            // Handle update logic here
            navController.popBackStack()
        }
    )
}

@Composable
fun UpdateCandidateContent(
    candidateId: String,
    onUpdateClick: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Update Candidate",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Candidate ID: $candidateId",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Candidate Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onUpdateClick(name, position) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Candidate")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateCandidatePreview() {
    VotingappTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            UpdateCandidateContent(
                candidateId = "SAMPLE-123",
                onUpdateClick = { _, _ -> }
            )
        }
    }
}
