package com.example.votingsystem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.votingsystem.screens.dashboard.DashboardScreen
import com.example.votingsystem.screens.login.LoginScreen
import com.example.votingsystem.screens.register.RegisterScreen
import com.example.votingsystem.screens.vote.AddCandidateScreen
import com.example.votingsystem.screens.vote.CandidateListScreen
import com.example.votingsystem.screens.vote.UpdateCandidateScreen

const val ROUTE_REGISTER = "register"
const val ROUTE_LOGIN = "login"
const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_ADD_CANDIDATE = "add_candidate"
const val ROUTE_VIEW_CANDIDATE = "view_candidate"
const val ROUTE_UPDATE_CANDIDATE = "update_candidate"

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ADD_CANDIDATE
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }

        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(ROUTE_ADD_CANDIDATE) {
            AddCandidateScreen(navController)
        }

        composable(ROUTE_VIEW_CANDIDATE) {
            CandidateListScreen(navController)
        }

        composable(
            ROUTE_UPDATE_CANDIDATE,
            arguments = listOf(
                navArgument("candidateId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val candidateId = backStackEntry.arguments?.getString("candidateId")!!
            UpdateCandidateScreen(navController, candidateId)
        }
    }
}