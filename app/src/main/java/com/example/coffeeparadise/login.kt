package com.example.coffeeparadise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.coffeeparadise.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // Use the correct binding class for login
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            validateAndLogin()
        }

        binding.signupText.setOnClickListener { // Assuming you have a TextView with id signupText in activity_login.xml
            Toast.makeText(this, "Navigating to Sign Up page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, signup::class.java) // Navigate to signup activity
            startActivity(intent)
            finish() // Optional: Finish login activity so user can't go back with back button
        }
    }

    private fun validateAndLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        // Clear previous errors
        binding.emailTextInputLayout.error = null
        binding.passwordTextInputLayout.error = null

        var isValid = true

        if (email.isEmpty()) {
            binding.emailTextInputLayout.error = "Email cannot be empty"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextInputLayout.error = "Invalid email format"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordTextInputLayout.error = "Password cannot be empty"
            isValid = false
        }

        if (isValid) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "Login successful! Welcome back!", Toast.LENGTH_SHORT).show()
                        // Navigate to your main application activity (e.g., MainActivity)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Finish login activity so user can't go back to it
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidUserException -> "No user found with this email."
                            is FirebaseAuthInvalidCredentialsException -> "Wrong password. Please try again."
                            else -> "Authentication failed: ${task.exception?.message}"
                        }
                        Toast.makeText(baseContext, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show()
        }
    }
}