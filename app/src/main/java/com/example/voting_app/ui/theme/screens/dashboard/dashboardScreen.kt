package com.example.votingapp.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.votingapp.auth.AuthViewModel
import com.example.votingapp.navigation.ROUTE_CANDIDATES
import com.example.votingapp.navigation.ROUTE_RESULTS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    val selectedItem = remember { mutableStateOf(0) }
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Voting System",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout(navController, context)
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { Icon(Icons.Default.HowToVote, null) },
                    label = { Text("Vote") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = { Icon(Icons.Default.BarChart, null) },
                    label = { Text("Results") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = { selectedItem.value = 2 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Welcome to Online Voting",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // QUICK STATS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VoteCard("5", "Candidates")
                VoteCard("120", "Votes Cast")
                VoteCard("1", "Election")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ACTION CARDS
            ActionCard(
                title = "View Candidates",
                desc = "See all running candidates",
                onClick = { navController.navigate(ROUTE_CANDIDATES) }
            )

            ActionCard(
                title = "View Results",
                desc = "Check live voting results",
                onClick = { navController.navigate(ROUTE_RESULTS) }
            )
        }
    }
}