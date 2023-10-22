package com.example.mahnyoh

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mahnyoh.ui.theme.MahnYohTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = Firebase.auth.currentUser
        if (user != null) {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
        else {

            setContentView(R.layout.login)
            auth = FirebaseAuth.getInstance()

            var signup=findViewById<TextView>(R.id.signup_button)
            var login = findViewById<Button>(R.id.login_button)
            val email = findViewById<EditText>(R.id.eMail)
            val password = findViewById<EditText>(R.id.passwords)
            login.setOnClickListener {

                val mail = email.text.toString()
                val pass = password.text.toString()

                if (mail.isEmpty()) {
                    Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT)
                        .show()
                    email.requestFocus()
                    return@setOnClickListener

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT)
                        .show()

                    email.requestFocus()
                    return@setOnClickListener
                }

                if (pass.isEmpty()) {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()

                    password.requestFocus()
                    return@setOnClickListener
                }



                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Welcome $email", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            signup.setOnClickListener {
                val intent=Intent(this,Signup::class.java)
                startActivity(intent)
            }


        }
    }


}
