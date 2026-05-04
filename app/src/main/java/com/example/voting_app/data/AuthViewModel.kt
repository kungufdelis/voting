package com.example.voting_app.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.voting_app.navigation.ROUTE_DASHBOARD
import com.example.voting_app.navigation.ROUTE_LOGIN
import com.example.voting_app.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

data class UserModel(
    var username: String = "",
    var email: String = "",
    var userId: String = "",
    var nationalId: String = ""
)
fun signup(
    username: String,
    email: String,
    phone: String,
    nationalId: String,
    password: String,
    confirmPassword: String,
    navController: NavController,
    context: Context
) {

    if (username.isBlank() || email.isBlank() || nationalId.isBlank()
        || password.isBlank() || confirmPassword.isBlank()
    ) {
        Toast.makeText(context, "All fields required", Toast.LENGTH_LONG).show()
        return
    }

    if (password != confirmPassword) {
        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
        return
    }

    val dbRef = FirebaseDatabase.getInstance().getReference("Users")

    // 🔐 Check duplicate National ID
    dbRef.orderByChild("nationalId").equalTo(nationalId)
        .get().addOnSuccessListener { snapshot ->

            if (snapshot.exists()) {
                Toast.makeText(context, "National ID already registered", Toast.LENGTH_LONG).show()
            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""

                            val user = UserModel(
                                username = username,
                                email = email,
                                userId = userId,
                                nationalId = nationalId
                            )

                            saveUserToDatabase(user, navController, context)

                        } else {
                            Toast.makeText(
                                context,
                                task.exception?.message ?: "Registration failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
}
fun login(
    email: String,
    password: String,
    navController: NavController,
    context: Context
) {

    if (email.isBlank() || password.isBlank()) {
        Toast.makeText(context, "Email and Password required", Toast.LENGTH_LONG).show()
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val userId = auth.currentUser?.uid ?: ""

                val voteRef = FirebaseDatabase.getInstance()
                    .getReference("Votes")
                    .child(userId)

                voteRef.get().addOnSuccessListener { snapshot ->

                    if (snapshot.exists()) {
                        Toast.makeText(context, "You already voted", Toast.LENGTH_LONG).show()
                        navController.navigate("results")
                    } else {
                        navController.navigate("ballot")
                    }
                }

            } else {
                Toast.makeText(
                    context,
                    task.exception?.message ?: "Login failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
}


