package com.example.voting_app.ui.theme.screens.votedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.voting_app.data.VoteViewModel
import com.example.voting_app.models.CandidateModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun VoteDetailScreen(
    navController: NavHostController,
    voteId: String
) {
    val context = LocalContext.current
    val voteViewModel: VoteViewModel = viewModel()
    val candidates = remember { mutableStateListOf<CandidateModel>() }
    var selectedCandidateId by remember { mutableStateOf("") }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Fetch candidates from Firebase
    LaunchedEffect(Unit) {
        val ref = FirebaseDatabase.getInstance().getReference("Candidates")
        ref.get().addOnSuccessListener { snapshot ->
            candidates.clear()
            for (child in snapshot.children) {
                val candidate = child.getValue(CandidateModel::class.java)
                if (candidate != null) {
                    candidates.add(candidate)
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Cast Your Vote",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Election ID: $voteId",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (candidates.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            Text("Loading candidates...", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(candidates) { candidate ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedCandidateId = candidate.id },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedCandidateId == candidate.id),
                            onClick = { selectedCandidateId = candidate.id }
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(text = candidate.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = candidate.position, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    HorizontalDivider()
                }
            }

            Button(
                onClick = {
                    if (selectedCandidateId.isNotEmpty()) {
                        voteViewModel.castVote(userId, selectedCandidateId, context, navController)
                    } else {
                        // Show warning
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                enabled = selectedCandidateId.isNotEmpty()
            ) {
                Text("Submit Vote")
            }
        }
    }
}
