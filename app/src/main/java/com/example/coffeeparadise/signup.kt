package com.example.coffeeparadise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.coffeeparadise.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth // Import FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException // Import for specific error handling

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth // Declare FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set the content view from the binding object

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

        binding.signupButton.setOnClickListener {
            validateAndSignUp()
        }

        binding.loginText.setOnClickListener {
            Toast.makeText(this, "Navigating to Login page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    private fun validateAndSignUp() {
        // Access views and their text using the binding object directly
        val username = binding.usernameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        // Clear previous errors
        binding.usernameTextInputLayout.error = null
        binding.emailTextInputLayout.error = null
        binding.passwordTextInputLayout.error = null
        binding.confirmPasswordTextInputLayout.error = null

        var isValid = true

        if (username.isEmpty()) {
            binding.usernameTextInputLayout.error = "Username cannot be empty"
            isValid = false
        }

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
        } else if (password.length < 6) {
            binding.passwordTextInputLayout.error = "Password must be at least 6 characters long"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordTextInputLayout.error = "Confirm password cannot be empty"
            isValid = false
        } else if (password != confirmPassword) {
            binding.confirmPasswordTextInputLayout.error = "Passwords do not match"
            isValid = false
        }

        if (isValid) {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignUp", "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "Sign up successful! Welcome to Coffee Paradise!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, login::class.java) // Assuming you want to go to login after signup
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthUserCollisionException -> "This email address is already registered."
                            else -> "Authentication failed: ${task.exception?.message}"
                        }
                        Toast.makeText(baseContext, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please correct the errors and try again.", Toast.LENGTH_SHORT).show()
        }
    }
}