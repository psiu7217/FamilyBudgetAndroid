package com.example.myapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val PREFS_KEY_IS_LOGGED_IN = "isLoggedIn"


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
//        val isLoggedIn = prefs.getBoolean(PREFS_KEY_IS_LOGGED_IN, false)
        val email = prefs.getString("email", "")
        val displayName = prefs.getString("name", "")

//        val email = intent.getStringExtra("email")
//        val displayName = intent.getStringExtra("name")

        findViewById<TextView>(R.id.textView).text = "$email \n $displayName"

        findViewById<Button>(R.id.singOutBtn).setOnClickListener{
            val editor = getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
            editor.putBoolean(PREFS_KEY_IS_LOGGED_IN, false)
            editor.apply()

            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}