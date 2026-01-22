package com.example.aiubsports

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class StudentDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        val tvWelcome: TextView = findViewById(R.id.tvWelcome)

        val cardNews: CardView = findViewById(R.id.cardStudentNews)
        val cardSlot: CardView = findViewById(R.id.cardBookSlot)
        val cardTourney: CardView = findViewById(R.id.cardJoinTournament)
        val cardProfile: CardView = findViewById(R.id.cardProfile) // New Profile Card

        val btnLogout: Button = findViewById(R.id.btnLogout)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("users").child(userId).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val name = it.child("name").value.toString()
                        val studentId = it.child("studentId").value.toString()

                        tvWelcome.text = "Welcome, $name"
                        //tvId.text = "ID: $studentId"
                    }
                }
        }


        cardNews.setOnClickListener {
            startActivity(Intent(this, StudentViewNewsActivity::class.java))
        }

        cardSlot.setOnClickListener {
            startActivity(Intent(this, StudentBookSlotActivity::class.java))
        }

        cardTourney.setOnClickListener {
            startActivity(Intent(this, StudentTournamentActivity::class.java))
        }

        cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}