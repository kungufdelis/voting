package com.example.votingapp.model

data class UserModel(
    val userId: String,
    val username: String,
    val email: String,
    val hasVoted: Boolean = false,
    val selectedOptionId: String? = null,
    val voteTimestamp: Long? = null
)