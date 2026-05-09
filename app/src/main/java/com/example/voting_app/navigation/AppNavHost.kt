package com.example.voting_app.navigation

import androidx.compose.runtime.Composable
import com.example.voting_app.ui.theme.screens.splash.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.voting_app.ui.theme.screens.addcandidate.AddCandidateScreen
import com.example.voting_app.ui.theme.screens.dashboard.DashboardScreen
import com.example.voting_app.ui.theme.screens.login.LoginScreen
import com.example.voting_app.ui.theme.screens.results.ResultsScreen
import com.example.voting_app.ui.theme.screens.update.UpdateCandidateScreen
import com.example.voting_app.ui.theme.screens.view.ViewCandidateScreen
import com.example.voting_app.ui.theme.screens.votedetail.VoteDetailScreen
import com.example.voting_app.ui.theme.screens.votelist.VoteListScreen
import com.example.votingapp.ui.screens.RegisterScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // Splash
        composable(ROUTE_SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(ROUTE_DASHBOARD) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                }
            )
        }


        // Register
        composable(ROUTE_REGISTER) {
           RegisterScreen(
               onNavigateToLogin = {
               navController.navigate(ROUTE_LOGIN) {
                   popUpTo(ROUTE_SPLASH) { inclusive = true }
               }
           },
               onRegisterSuccess = {
                navController.navigate(ROUTE_DASHBOARD) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
            )
        }

        // Login
        composable(ROUTE_LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(ROUTE_DASHBOARD) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(ROUTE_REGISTER) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Dashboard
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }

        // Vote List
        composable(ROUTE_VOTE_LIST) {
            VoteListScreen(navController)
        }

        // Vote Detail
        composable(
            "$ROUTE_VOTE_DETAIL/{voteId}",
            arguments = listOf(
                navArgument("voteId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val voteId = backStackEntry.arguments?.getString("voteId") ?: ""
            VoteDetailScreen(navController, voteId)
        }

        // Results
        composable(
            "$ROUTE_RESULTS/{voteId}",
            arguments = listOf(
                navArgument("voteId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val voteId = backStackEntry.arguments?.getString("voteId") ?: ""
            ResultsScreen(navController, voteId)
        }

        // Update Candidate
        composable(
            "$ROUTE_UPDATE_CANDIDATE/{voteId}",
            arguments = listOf(
                navArgument("voteId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val voteId = backStackEntry.arguments?.getString("voteId") ?: ""
            UpdateCandidateScreen(navController, voteId)
        }

        // View Candidate
        composable(ROUTE_VIEW_CANDIDATE) {
            ViewCandidateScreen(navController)
        }

        // Add Candidate
        composable(ROUTE_ADD_CANDIDATE) {
            AddCandidateScreen(navController)
        }
    }
}
