package com.example.aiubsports

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName = findViewById<TextView>(R.id.tvProfileName)
        val tvUniId = findViewById<TextView>(R.id.tvProfileUniId)
        val tvEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val ref = FirebaseDatabase.getInstance().getReference("users").child(currentUser.uid)

            ref.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val userProfile = snapshot.getValue(User::class.java)

                    if (userProfile != null) {
                        tvName.text = userProfile.name ?: "No Name"
                        tvUniId.text = userProfile.universityId ?: "No ID"
                        tvEmail.text = userProfile.email ?: currentUser.email
                    }
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}