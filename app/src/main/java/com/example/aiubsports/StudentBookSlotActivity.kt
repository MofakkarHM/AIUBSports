package com.example.aiubsports

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StudentBookSlotActivity : AppCompatActivity() {

    private lateinit var rvSlots: RecyclerView
    private lateinit var slotList: ArrayList<Slot>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_slot)

        rvSlots = findViewById(R.id.rvStudentSlots)
        rvSlots.layoutManager = LinearLayoutManager(this)

        slotList = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("slots")

        loadSlots()
    }

    private fun loadSlots() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                slotList.clear()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val slot = data.getValue(Slot::class.java)
                        if (slot != null) {
                            slotList.add(slot)
                        }
                    }
                    rvSlots.adapter = StudentSlotAdapter(slotList) { slot ->
                        bookSlot(slot)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun bookSlot(slot: Slot) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return


        val updates = mapOf(
            "status" to "Booked",
            "bookedBy" to userId
        )

        if (slot.slotId != null) {
            database.child(slot.slotId).updateChildren(updates).addOnSuccessListener {
                Toast.makeText(this, "Slot Booked Successfully!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}