package com.example.mahnyoh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Signup : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.signup)

        val signup=findViewById<Button>(R.id.signup)
        val name=findViewById<TextInputLayout>(R.id.name)
        val uname=findViewById<TextInputLayout>(R.id.username)
        val mail=findViewById<TextInputLayout>(R.id.email)
        val pass=findViewById<TextInputLayout>(R.id.password)
        val c_pass=findViewById<TextInputLayout>(R.id.conPass)

        signup.setOnClickListener {

            val name= name.editText?.text.toString()
            val uname= uname.editText?.text.toString()
            val email = mail.editText?.text.toString()
            val pass = pass.editText?.text.toString()
            val confirmPass = c_pass.editText?.text.toString()
            firebaseAuth=FirebaseAuth.getInstance()



            if (name.isNotEmpty()&& uname.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {


                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {



                            val user = User(name, uname, email)
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                                .setValue(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "User has been registered successfully", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                    } else {
                                        Toast.makeText(this, "Registration failed!", Toast.LENGTH_LONG).show()
                                    }
                                }


                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }


}