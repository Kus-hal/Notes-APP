package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.notesapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            signUp()
        }

        binding.username.addTextChangedListener {
            binding.username.error = null
        }

        binding.password.addTextChangedListener {
            binding.password.error = null
        }

        binding.rePassword.addTextChangedListener {
            binding.rePassword.error = null
        }
    }

    private fun signUp() {
        val email = binding.username.text.toString()
        val pass = binding.password.text.toString()
        val confirmPass = binding.rePassword.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
            if (pass == confirmPass) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.rePassword.error = "Password does not match"
            }
        } else {
            if (email.isEmpty()) {
                binding.username.error = "Email cannot be empty"
            }
            if (pass.isEmpty()) {
                binding.password.error = "Password cannot be empty"
            }
            if (confirmPass.isEmpty()) {
                binding.rePassword.error = "Please re-enter password"
            }
        }
    }
}