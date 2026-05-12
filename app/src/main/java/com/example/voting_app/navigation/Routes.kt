package com.example.voting_app.navigation

const val ROUTE_SPLASH = "splash"
const val ROUTE_REGISTER = "register"
const val ROUTE_LOGIN = "login"
const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_VOTE_LIST = "vote_list"
const val ROUTE_VOTE_DETAIL = "vote_detail"
const val ROUTE_RESULTS = "results"
const val ROUTE_UPDATE_CANDIDATE = "update_candidate"
const val ROUTE_VIEW_CANDIDATE = "view_candidate"
const val ROUTE_ADD_CANDIDATE = "add_candidate"

// Helper functions (important for navigation with IDs)
fun voteDetailRoute(voteId: String): String {
    return "$ROUTE_VOTE_DETAIL/$voteId"
}

fun resultsRoute(voteId: String): String {
    return "$ROUTE_RESULTS/$voteId"
}

fun updateCandidateRoute(voteId: String): String {
    return "$ROUTE_UPDATE_CANDIDATE/$voteId"
}
