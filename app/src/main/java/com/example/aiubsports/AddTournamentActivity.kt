package com.example.aiubsports

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddTournamentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament)

        val etName = findViewById<EditText>(R.id.etTourneyName)
        val etSport = findViewById<EditText>(R.id.etSportType)
        val etDate = findViewById<EditText>(R.id.etLastDate)
        val btnCreate = findViewById<Button>(R.id.btnCreateTourney)
        val database = FirebaseDatabase.getInstance().getReference("tournaments")

        btnCreate.setOnClickListener {
            val name = etName.text.toString().trim()
            val sport = etSport.text.toString().trim()
            val date = etDate.text.toString().trim()

            if (name.isNotEmpty() && sport.isNotEmpty()) {
                val id = database.push().key
                if (id != null) {
                    val t = Tournament(id, name, sport, date, "Open")
                    database.child(id).setValue(t).addOnSuccessListener {
                        Toast.makeText(this, "Tournament Announced!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}