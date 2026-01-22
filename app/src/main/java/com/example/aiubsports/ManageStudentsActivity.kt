package com.example.aiubsports

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ManageStudentsActivity : AppCompatActivity() {

    private lateinit var rvStudents: RecyclerView
    private lateinit var studentList: ArrayList<User>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_students)

        rvStudents = findViewById(R.id.rvStudents)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.setHasFixedSize(true)

        studentList = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("users")

        getStudentData()
    }

    private fun getStudentData() {
        // Query users where 'role' is equal to 'Student'
        val query = database.orderByChild("role").equalTo("Student")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null) {
                            studentList.add(user)
                        }
                    }
                    rvStudents.adapter = StudentAdapter(studentList) { userId ->
                        deleteStudent(userId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteStudent(userId: String) {

        database.child(userId).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Student Removed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to remove", Toast.LENGTH_SHORT).show()
        }
    }
}