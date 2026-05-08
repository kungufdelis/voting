package com.example.voting_app.ui.theme.screens.update

import android.R.attr.password
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.voting_app.navigation.ROUTE_UPDATE_CANDIDATE

@Composable
fun UpdateCandidateScreen(
    navController: NavController,
    candidateId: String
) {

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Update Candidate: $candidateId",
            style = MaterialTheme.typography.headlineMedium
        )

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
                val voteId = ""
                navController.navigate("$ROUTE_UPDATE_CANDIDATE/$voteId")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Candidate")
        }
    }


    }
