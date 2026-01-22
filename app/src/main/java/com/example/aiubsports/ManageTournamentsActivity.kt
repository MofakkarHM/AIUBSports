package com.example.aiubsports

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ManageTournamentsActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var list: ArrayList<Tournament>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournaments)

        rv = findViewById(R.id.rvTournaments)
        val fab = findViewById<FloatingActionButton>(R.id.fabAddTournament)

        rv.layoutManager = LinearLayoutManager(this)
        list = arrayListOf()

        fab.setOnClickListener {
            startActivity(Intent(this, AddTournamentActivity::class.java))
        }

        getTournaments()
    }

    private fun getTournaments() {
        FirebaseDatabase.getInstance().getReference("tournaments")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (data in snapshot.children) {
                        val t = data.getValue(Tournament::class.java)
                        if (t != null) list.add(t)
                    }


                    list.reverse()


                    rv.adapter = TournamentAdapter(
                        list,
                        onViewTeamsClick = { id, name ->

                            val intent = Intent(this@ManageTournamentsActivity, ViewTeamsActivity::class.java)
                            intent.putExtra("tournamentId", id)
                            intent.putExtra("tournamentName", name)
                            startActivity(intent)
                        },
                        onDeleteClick = { id ->

                            deleteTournament(id)
                        }
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun deleteTournament(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("tournaments").child(id)

        dbRef.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Tournament Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
        }
    }
}