package com.example.aiubsports

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StudentTournamentActivity : AppCompatActivity() {

    private lateinit var rvTournaments: RecyclerView
    private lateinit var tournamentList: ArrayList<Tournament>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_tournament)

        rvTournaments = findViewById(R.id.rvStudentTournaments)
        rvTournaments.layoutManager = LinearLayoutManager(this)

        tournamentList = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("tournaments")

        loadTournaments()
    }

    private fun loadTournaments() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tournamentList.clear()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val t = data.getValue(Tournament::class.java)
                        if (t != null) tournamentList.add(t)
                    }
                    rvTournaments.adapter = StudentTournamentAdapter(tournamentList) { selectedTournament ->
                        showJoinDialog(selectedTournament)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }


    private fun showJoinDialog(tournament: Tournament) {
        val dialogView = LinearLayout(this)
        dialogView.orientation = LinearLayout.VERTICAL
        dialogView.setPadding(50, 40, 50, 10)

        val etTeamName = EditText(this)
        etTeamName.hint = "Enter Team Name"
        dialogView.addView(etTeamName)

        val etMembers = EditText(this)
        etMembers.hint = "Enter Member Names (comma separated)"
        dialogView.addView(etMembers)

        AlertDialog.Builder(this)
            .setTitle("Join ${tournament.name}")
            .setView(dialogView)
            .setPositiveButton("Submit") { _, _ ->
                val teamName = etTeamName.text.toString()
                val members = etMembers.text.toString()
                if (teamName.isNotEmpty() && members.isNotEmpty()) {
                    submitApplication(tournament.tournamentId!!, teamName, members)
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun submitApplication(tourneyId: String, teamName: String, members: String) {
        val appRef = FirebaseDatabase.getInstance().getReference("tournament_applications")
        val appId = appRef.push().key ?: return

        val app = TeamApp(appId, tourneyId, teamName, members, "Pending")

        appRef.child(appId).setValue(app).addOnSuccessListener {
            Toast.makeText(this, "Application Sent!", Toast.LENGTH_SHORT).show()
        }
    }
}