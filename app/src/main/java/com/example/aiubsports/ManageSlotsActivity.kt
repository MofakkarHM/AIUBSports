package com.example.aiubsports

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ManageSlotsActivity : AppCompatActivity() {

    private lateinit var rvSlots: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var slotList: ArrayList<Slot>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_slots)

        rvSlots = findViewById(R.id.rvSlots)
        fabAdd = findViewById(R.id.fabAddSlot)

        rvSlots.layoutManager = LinearLayoutManager(this)
        rvSlots.setHasFixedSize(true)
        slotList = arrayListOf()

        database = FirebaseDatabase.getInstance().getReference("slots")

        // Go to Add Slot Screen
        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddSlotActivity::class.java))
        }

        getSlotsData()
    }

    private fun getSlotsData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                slotList.clear()
                if (snapshot.exists()) {
                    for (slotSnapshot in snapshot.children) {
                        val slot = slotSnapshot.getValue(Slot::class.java)
                        if (slot != null) {
                            slotList.add(slot)
                        }
                    }
                    rvSlots.adapter = SlotAdapter(slotList) { slotId ->
                        deleteSlot(slotId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteSlot(slotId: String) {
        database.child(slotId).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Slot Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}