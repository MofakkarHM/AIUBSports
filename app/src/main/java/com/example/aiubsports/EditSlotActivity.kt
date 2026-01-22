package com.example.aiubsports

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditSlotActivity : AppCompatActivity() {

    private lateinit var etTime: EditText
    private lateinit var etDate: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnUnbook: Button
    private var slotId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_slot)

        etTime = findViewById(R.id.etEditSlotTime)
        etDate = findViewById(R.id.etEditSlotDate)
        btnUpdate = findViewById(R.id.btnUpdateSlot)
        btnUnbook = findViewById(R.id.btnUnbookSlot)

        // Receive Data
        slotId = intent.getStringExtra("slotId")
        etTime.setText(intent.getStringExtra("time"))
        etDate.setText(intent.getStringExtra("date"))

        btnUpdate.setOnClickListener {
            updateSlotDetails()
        }

        btnUnbook.setOnClickListener {
            cancelBooking()
        }
    }

    private fun updateSlotDetails() {
        val newTime = etTime.text.toString().trim()
        val newDate = etDate.text.toString().trim()

        if (slotId != null && newTime.isNotEmpty()) {
            val updates = mapOf(
                "time" to newTime,
                "date" to newDate
            )
            FirebaseDatabase.getInstance().getReference("slots").child(slotId!!)
                .updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Slot Updated!", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    private fun cancelBooking() {
        if (slotId != null) {
            val updates = mapOf(
                "status" to "Available",
                "bookedBy" to null
            )
            FirebaseDatabase.getInstance().getReference("slots").child(slotId!!)
                .updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Booking Cancelled. Slot is now Available.", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}