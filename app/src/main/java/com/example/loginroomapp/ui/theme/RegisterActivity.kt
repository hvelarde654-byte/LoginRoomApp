package com.example.loginroomapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.loginroomapp.data.AppDatabase
import com.example.loginroomapp.data.User
import kotlinx.coroutines.launch
import androidx.activity.ComponentActivity
class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvError = findViewById<TextView>(R.id.tvError)

        val db = AppDatabase.getInstance(this)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                tvError.text = "Completa todos los campos"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existingUser = db.userDao().findByUsername(username)
                if (existingUser != null) {
                    tvError.text = "El usuario ya existe"
                } else {
                    val newUser = User(fullName = fullName, username = username, password = password)
                    db.userDao().insertUser(newUser)
                    Toast.makeText(this@RegisterActivity, "¡Usuario registrado!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}