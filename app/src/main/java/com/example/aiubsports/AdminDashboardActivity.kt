package com.example.aiubsports

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val cardNews: CardView = findViewById(R.id.cardManageNews)
        val cardSlots: CardView = findViewById(R.id.cardManageSlots)
        val cardStudents: CardView = findViewById(R.id.cardManageStudents)

        val cardTournaments: CardView = findViewById(R.id.cardManageTournaments)
        val btnLogout: Button = findViewById(R.id.btnLogout)


        cardNews.setOnClickListener {
            startActivity(Intent(this, ManageNewsActivity::class.java))
        }


        cardSlots.setOnClickListener {
            startActivity(Intent(this, ManageSlotsActivity::class.java))
        }


        cardStudents.setOnClickListener {
            startActivity(Intent(this, ManageStudentsActivity::class.java))
        }


        cardTournaments.setOnClickListener {

            startActivity(Intent(this, ManageTournamentsActivity::class.java))
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