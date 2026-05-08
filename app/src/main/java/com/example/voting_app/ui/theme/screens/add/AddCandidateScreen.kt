package com.example.voting_app.ui.theme.screens.addcandidate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.voting_app.data.VoteViewModel

@Composable
fun AddCandidateScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    val context = LocalContext.current
    val voteViewModel: VoteViewModel = viewModel()

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Add Candidate", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Candidate Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                voteViewModel.addCandidate(name, position, context, navController)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Candidate")
        }
    }
}
