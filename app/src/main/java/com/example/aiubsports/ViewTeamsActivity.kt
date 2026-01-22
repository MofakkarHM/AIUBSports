package com.example.aiubsports

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ViewTeamsActivity : AppCompatActivity() {

    private lateinit var rvTeams: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var teamList: ArrayList<TeamApp>
    private lateinit var database: DatabaseReference
    private var currentTournamentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_teams)


        currentTournamentId = intent.getStringExtra("tournamentId")
        val tournamentName = intent.getStringExtra("tournamentName")

        tvTitle = findViewById(R.id.tvTournamentTitle)
        rvTeams = findViewById(R.id.rvTeams)

        tvTitle.text = "Teams for: $tournamentName"

        rvTeams.layoutManager = LinearLayoutManager(this)
        teamList = arrayListOf()


        database = FirebaseDatabase.getInstance().getReference("tournament_applications")

        if (currentTournamentId != null) {
            getTeams()
        }
    }

    private fun getTeams() {
        val query = database.orderByChild("tournamentId").equalTo(currentTournamentId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamList.clear()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val team = data.getValue(TeamApp::class.java)

                        if (team != null) {

                            val teamWithId = team.copy(applicationId = data.key)
                            teamList.add(teamWithId)
                        }
                    }

                    rvTeams.adapter = TeamAdapter(teamList) { appId, newStatus ->
                        updateTeamStatus(appId, newStatus)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateTeamStatus(appId: String, status: String) {
        database.child(appId).child("status").setValue(status)
            .addOnSuccessListener {
                Toast.makeText(this, "Team $status", Toast.LENGTH_SHORT).show()
            }
    }
}