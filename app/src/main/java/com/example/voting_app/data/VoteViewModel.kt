package com.example.voting_app.data

import android.content.Context
import android.widget.Toast

class VoteViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("Votes")

    // 🗳️ CAST VOTE
    fun castVote(
        voterId: String,
        candidate: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val voteRef = db.child(voterId)

                val voteData = mapOf(
                    "voterId" to voterId,
                    "candidate" to candidate
                )

                voteRef.setValue(voteData).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Vote submitted", Toast.LENGTH_LONG).show()
                    navController.navigate("results")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Vote failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // 📊 FETCH RESULTS
    private val _results = mutableStateListOf<String>()
    val results: List<String> = _results

    fun fetchResults(context: Context) {
        db.get().addOnSuccessListener { snapshot ->
            _results.clear()

            for (child in snapshot.children) {
                val candidate = child.child("candidate").value.toString()
                _results.add(candidate)
            }

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load results", Toast.LENGTH_LONG).show()
        }
    }
}