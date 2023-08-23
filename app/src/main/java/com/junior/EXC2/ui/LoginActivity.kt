package com.junior.EXC2.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junior.EXC2.R
import com.junior.EXC2.databinding.ActivityLoginBinding




// login cod
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>
    private var errorMessageShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            goToMainActivity()
            return
        }

        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    authenticateWithFirebase(account.idToken!!)
                } catch (e: Exception) {
                    // Manejar la excepción
                }
            }
        }

        binding.tilEmail.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(text.toString(), binding.tilPassword.editText?.text.toString())
        }

        binding.tilPassword.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(binding.tilEmail.editText?.text.toString(), text.toString())
        }

        binding.btnLogin.setOnClickListener {
            val password = binding.tilPassword.editText?.text.toString()
            val email = binding.tilEmail.editText?.text.toString()

            if (validateEmailPassword(email, password)) {
                errorMessageShown = false // Restablecer la variable
                loginWithEmailAndPassword(email, password)
            } else {
                if (!errorMessageShown) {
                    sendMessage("Email y Password Incorrectos")
                    errorMessageShown = true // Marcar que el mensaje se ha mostrado
                }
            }
        }


        binding.btnGoogle.setOnClickListener {
            loginWithGoogle()
        }

        binding.btnSingUp.setOnClickListener {
            val password = binding.tilPassword.editText?.text.toString()
            val email = binding.tilEmail.editText?.text.toString()

            if (validateEmailPassword(email, password)) {
                errorMessageShown = false // Restablecer la variable
                signUpWithEmailAndPassword(email, password)
                sendMessage("Registro exitoso") // Mensaje de registro exitoso
            } else {
                sendMessage("Datos mal ingresados")
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                } else {
                    Toast.makeText(
                        this,
                        "El usuario no está registrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String) {
        if (validateEmailPassword(email, password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "El usuario fue registrado correctamente", Toast.LENGTH_SHORT).show()
                        goToMainActivity()
                    } else {
                        Toast.makeText(this, "Ocurrió un error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "No son los datos correctos.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun authenticateWithFirebase(idToken: String) {
        val authCredentials = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(authCredentials)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                }
            }//
    }

    private fun loginWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val intent = googleClient.signInIntent
        googleLauncher.launch(intent)
    }

    private fun validateEmailPassword(email: String, password: String): Boolean {
        val isEmailValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email == "ejemplo@idat.edu.pe"
        val isPasswordValid = password.isNotEmpty() && password == "123456"
        if (!isEmailValid || !isPasswordValid) {
            return false
        }
        return true
    }


    private fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
