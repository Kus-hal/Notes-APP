package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import kotlinx.coroutines.tasks.await
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var oneTapClient: SignInClient? = null
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id)) // Replace with your Web client ID
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

        binding.signupText.setOnClickListener{
            val i= Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnRegister.setOnClickListener {
            signInWithEmailPassword()
        }

        binding.Google.setOnClickListener {
            signingGoogle()
        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    val credential = Identity.getSignInClient(this)
                        .getSignInCredentialFromIntent(intent)
                    val idToken = credential.googleIdToken
                    idToken?.let { signInWithGoogle(it) }
                }
            }
    }

    private fun signInWithEmailPassword() {
        val email = binding.signinMailId.text.toString()
        val password = binding.signinPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val intent = Intent(this, NotesActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this, "Please enter email and password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signingGoogle() {
        oneTapClient?.beginSignIn(signInRequest)
            ?.addOnSuccessListener { result ->
                val signInIntent = result.pendingIntent.intentSender
                val intentSenderRequest = IntentSenderRequest.Builder(signInIntent).build()
                val intent = Intent()
                intent.putExtra("IntentSenderRequest", intentSenderRequest)
                activityResultLauncher.launch(intent)
            }
            ?.addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(
                    this@LoginActivity,
                    "Failed to start Google sign-in: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }



    private fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}


