package com.example.voting_app.navigation

const val ROUTE_SPLASH = "splash/{voteId}"
const val ROUTE_REGISTER = "register/{voteId}"
const val ROUTE_LOGIN = "login/{voteId}"
const val ROUTE_DASHBOARD = "dashboard/{voteId}"
const val ROUTE_VOTE_LIST = "vote_list/{voteId}"
const val ROUTE_VOTE_DETAIL = "vote_detail/{voteId}"
const val ROUTE_RESULTS = "results/{voteId}"
const val ROUTE_UPDATE_CANDIDATE = "update_candidate/{voteId}"
const val ROUTE_VIEW_CANDIDATE = "view_candidate/{voteId}"
const val ROUTE_ADD_CANDIDATE = "add_candidate/{voteId}"





// 🔥 Helper functions (important for navigation with IDs)
fun voteDetailRoute(voteId: String): String {
    return "vote_detail/$voteId"
}

fun resultsRoute(voteId: String): String {
    return "results/$voteId"
}
