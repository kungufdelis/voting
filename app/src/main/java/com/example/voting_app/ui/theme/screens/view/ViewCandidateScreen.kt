package com.example.voting_app.ui.theme.screens.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.voting_app.data.VoteViewModel
import com.example.voting_app.models.CandidateModel
import com.example.voting_app.navigation.ROUTE_UPDATE_CANDIDATE
import com.example.voting_app.ui.theme.VotingappTheme

@Composable
fun ViewCandidateScreen(navController: NavController, viewModel: VoteViewModel = viewModel()) {
    val context = LocalContext.current
    val candidates = viewModel.candidates

    LaunchedEffect(Unit) {
        viewModel.fetchCandidates(context)
    }

    ViewCandidateContent(
        candidates = candidates,
        onUpdateClick = { candidateId ->
            navController.navigate("$ROUTE_UPDATE_CANDIDATE/$candidateId")
        }
    )
}

@Composable
fun ViewCandidateContent(
    candidates: List<CandidateModel>,
    onUpdateClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Candidates",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(candidates) { candidate ->
                CandidateItem(
                    name = candidate.name,
                    onUpdateClick = {
                        onUpdateClick(candidate.id)
                    }
                )
            }
        }
    }
}

@Composable
fun CandidateItem(name: String, onUpdateClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3EDF7) // Light lavender background matching the image
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Text(
                text = "Tap to manage",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onUpdateClick,
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6750A4) // Purple background matching the image
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Update",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewCandidateScreenPreview() {
    VotingappTheme {
        val mockCandidates = listOf(
            CandidateModel(id = "1", name = "Alice"),
            CandidateModel(id = "2", name = "John"),
            CandidateModel(id = "3", name = "Mary")
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ViewCandidateContent(
                candidates = mockCandidates,
                onUpdateClick = {}
            )
        }
    }
}
