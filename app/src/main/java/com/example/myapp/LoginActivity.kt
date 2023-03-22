package com.example.myapp


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.ui.home.HomeFragment
import com.example.myapp.ui.home.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    // Key for shared preferences to store if user is already logged in
    private val PREFS_KEY_IS_LOGGED_IN = "isLoggedIn"


    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Check if user is already logged in
        val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean(PREFS_KEY_IS_LOGGED_IN, false)

        if (isLoggedIn) {
            // User is already logged in, skip login screen
            try {
                // some code that might throw an exception
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "An error occurred", e)
            }
        }


        // Initialize Firebase authentication and Google sign-in client
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSingInClient = GoogleSignIn.getClient(this, gso)

        // Set click listener for Google sign-in button
        findViewById<Button>(R.id.gSingInBtn).setOnClickListener{
            singInGoogle()
        }


    }

    private fun singInGoogle(){
        // Start Google sign-in flow
        val signInIntent = googleSingInClient.signInIntent
        launcher.launch(signInIntent)
    }

    // Register activity result launcher for Google sign-in flow
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
                if (result.resultCode == Activity.RESULT_OK){
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent: Intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("email", account.email)
//                intent.putExtra("name", account.displayName)

                val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean(PREFS_KEY_IS_LOGGED_IN, true)
                editor.putString("email", account.email)
                editor.putString("name", account.displayName)
                editor.apply()

                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
