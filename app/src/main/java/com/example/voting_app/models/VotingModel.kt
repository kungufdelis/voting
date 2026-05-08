package com.example.voting_app.models

data class VoterModel(
    var userId: String = "",
    var username: String = "",
    var nationalId: String = "",
    var email: String = ""
)

data class VoteModel(
    var voteId: String = "",
    var candidate: String = "",
    var timestamp: Long = System.currentTimeMillis()
)

data class ElectionModel(
    var electionId: String = "",
    var title: String = "",
    var status: String = "OPEN"
)

data class CandidateModel(
    var id: String = "",
    var name: String = "",
    var position: String = "",
    var voteCount: Int = 0
)