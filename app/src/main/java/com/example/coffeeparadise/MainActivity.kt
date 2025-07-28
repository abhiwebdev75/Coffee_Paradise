package com.example.coffeeparadise

import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.coffeeparadise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declare the binding object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set the content view from the binding object

        // No need for separate lateinit vars for each view like usernameEditText, emailEditText etc.
        // You can directly access them via the 'binding' object.
        // For example:
        // binding.usernameEditText
        // binding.emailEditText
        // binding.signupButton
        // binding.usernameTextInputLayout

        binding.signupButton.setOnClickListener {
            validateAndSignUp()
        }

        // Example of setting a click listener on the "Log In" text
        binding.loginText.setOnClickListener {
            Toast.makeText(this, "Navigating to Login page...", Toast.LENGTH_SHORT).show()
            // In a real app, you would navigate to your login activity here
            // val intent = Intent(this, LoginActivity::class.java)
            // startActivity(intent)
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
            // Simulate successful signup
            Toast.makeText(this, "Sign up successful! Welcome to Coffee Paradise!", Toast.LENGTH_LONG).show()
            // In a real app, you would send this data to your backend for user registration
            // For example:
            // val user = User(username, email, password)
            // authService.registerUser(user)

            // Optionally clear the fields after successful signup
            binding.usernameEditText.text?.clear()
            binding.emailEditText.text?.clear()
            binding.passwordEditText.text?.clear()
            binding.confirmPasswordEditText.text?.clear()
        } else {
            Toast.makeText(this, "Please correct the errors and try again.", Toast.LENGTH_SHORT).show()
        }
    }
}