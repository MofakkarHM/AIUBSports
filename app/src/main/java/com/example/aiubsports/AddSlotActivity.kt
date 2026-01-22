package com.example.aiubsports

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class AddSlotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_slot)

        val spinnerType = findViewById<Spinner>(R.id.spinnerType)
        val etDate = findViewById<EditText>(R.id.etSlotDate)
        val etTime = findViewById<EditText>(R.id.etSlotTime)
        val btnAdd = findViewById<Button>(R.id.btnAddSlot)


        val facilities = arrayOf("Gym", "Football Field", "Cricket Field", "Badminton Court")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, facilities)
        spinnerType.adapter = adapter


        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDate.setText(formattedDate)
            }, year, month, day)
            datePicker.show()
        }

        etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                val hour12 = if (selectedHour > 12) selectedHour - 12 else if (selectedHour == 0) 12 else selectedHour
                val formattedMinute = String.format("%02d", selectedMinute)

                val formattedTime = "$hour12:$formattedMinute $amPm"
                etTime.setText(formattedTime)
            }, hour, minute, false) // false = 12 hour format
            timePicker.show()
        }

        val database = FirebaseDatabase.getInstance().getReference("slots")

        btnAdd.setOnClickListener {
            val type = spinnerType.selectedItem.toString()
            val date = etDate.text.toString().trim()
            val time = etTime.text.toString().trim()

            if (date.isNotEmpty() && time.isNotEmpty()) {
                val slotId = database.push().key
                if (slotId != null) {
                    val slot = Slot(slotId, type, date, time, "Available", null)
                    database.child(slotId).setValue(slot).addOnSuccessListener {
                        Toast.makeText(this, "Slot Created Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show()
            }
        }
    }
}