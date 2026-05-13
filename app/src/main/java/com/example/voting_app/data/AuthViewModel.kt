package com.example.voting_app.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.voting_app.navigation.ROUTE_DASHBOARD
import com.example.voting_app.navigation.ROUTE_LOGIN
import com.example.voting_app.navigation.ROUTE_RESULTS

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val usersRef = FirebaseDatabase.getInstance().getReference("Users")
    private val votesRef = FirebaseDatabase.getInstance().getReference("Votes")


    fun signup(
        username: String,
        email: String,
        nationalId: String,
        password: String,
        confirmPassword: String,
        navController: NavController,
        context: Context
    ) {

        if (username.isBlank() || email.isBlank() || nationalId.isBlank()
            || password.isBlank() || confirmPassword.isBlank()
        ) {
            toast(context, "All fields are required")
            return
        }

        if (password != confirmPassword) {
            toast(context, "Passwords do not match")
            return
        }

        usersRef.orderByChild("nationalId").equalTo(nationalId)
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {
                    toast(context, "National ID already registered")
                } else {

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                val userId = auth.currentUser?.uid ?: ""

                                val userMap = hashMapOf(
                                    "username" to username,
                                    "email" to email,
                                    "nationalId" to nationalId,
                                    "userId" to userId
                                )

                                usersRef.child(userId).setValue(userMap)
                                    .addOnSuccessListener {
                                        toast(context, "Registration successful")
                                        navController.navigate(ROUTE_LOGIN)
                                    }
                                    .addOnFailureListener {
                                        toast(context, "Failed to save user info")
                                    }

                            } else {
                                toast(context, task.exception?.message ?: "Signup failed")
                            }
                        }
                }
            }
            .addOnFailureListener {
                toast(context, "Database error: ${it.message}")
            }
    }


    fun login(
        email: String,
        password: String,
        navController: NavController,
        context: Context
    ) {

        if (email.isBlank() || password.isBlank()) {
            toast(context, "Email and Password required")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""

                    // Check if user has already voted
                    votesRef.child(userId).get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                toast(context, "Welcome back! You already voted.")
                                // Corrected navigation to include the required argument
                                navController.navigate("$ROUTE_RESULTS/all") {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            } else {
                                navController.navigate(ROUTE_DASHBOARD) {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            }
                        }
                        .addOnFailureListener {
                            // If DB check fails, still allow them to see the dashboard
                            navController.navigate(ROUTE_DASHBOARD)
                        }

                } else {
                    val errorMessage = task.exception?.message ?: "Login failed"
                    toast(context, errorMessage)
                }
            }
    }

    fun logout(navController: NavController, context: Context) {
        auth.signOut()
        toast(context, "Logged out successfully")
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0)
        }
    }


    private fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
