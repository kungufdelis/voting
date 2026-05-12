package com.example.voting_app.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.voting_app.models.CandidateModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class VoteViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val votesRef = db.getReference("Votes")
    private val candidatesRef = db.getReference("Candidates")


    fun addCandidate(
        name: String,
        position: String,
        context: Context,
        navController: NavController
    ) {
        if (name.isBlank() || position.isBlank()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val candidateId = candidatesRef.push().key ?: return
        val candidate = CandidateModel(id = candidateId, name = name, position = position)

        candidatesRef.child(candidateId).setValue(candidate)
            .addOnSuccessListener {
                Toast.makeText(context, "Candidate added successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to add candidate: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun castVote(
        voterId: String,
        candidateId: String,
        context: Context,
        navController: NavController
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val voteSnapshot = votesRef.child(voterId).get().await()

                if (voteSnapshot.exists()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "You have already voted", Toast.LENGTH_LONG).show()
                        navController.navigate("results")
                    }
                    return@launch
                }

                val voteData = mapOf(
                    "voterId" to voterId,
                    "candidateId" to candidateId,
                    "timestamp" to System.currentTimeMillis()
                )

                votesRef.child(voterId).setValue(voteData).await()
                
                // Increment vote count for candidate
                val candidateVoteRef = candidatesRef.child(candidateId).child("voteCount")
                val currentCount = candidateVoteRef.get().await().getValue(Int::class.java) ?: 0
                candidateVoteRef.setValue(currentCount + 1).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Vote submitted successfully", Toast.LENGTH_LONG).show()
                    navController.navigate("results")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Vote failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private val _results = mutableStateListOf<String>()
    val results: List<String> = _results

    fun fetchResults(context: Context) {

        votesRef.get()
            .addOnSuccessListener { snapshot ->

                _results.clear()

                for (vote in snapshot.children) {
                    val candidateId = vote.child("candidateId").value.toString()
                    _results.add(candidateId)
                }

            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load results", Toast.LENGTH_LONG).show()
            }
    }

    private val _candidates = mutableStateListOf<CandidateModel>()
    val candidates: List<CandidateModel> = _candidates

    fun fetchCandidates(context: Context) {
        candidatesRef.get()
            .addOnSuccessListener { snapshot ->
                _candidates.clear()
                for (child in snapshot.children) {
                    val candidate = child.getValue(CandidateModel::class.java)
                    if (candidate != null) {
                        _candidates.add(candidate)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load candidates", Toast.LENGTH_LONG).show()
            }
    }

    fun clearVotes(context: Context) {
        votesRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "All votes cleared", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to clear votes", Toast.LENGTH_LONG).show()
            }
    }
}
