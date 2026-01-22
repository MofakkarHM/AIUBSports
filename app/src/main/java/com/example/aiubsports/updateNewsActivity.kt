package com.example.aiubsports

import android.os.Bundle
import android.widget.Button
import android.widget.EditText // IMPORT THIS
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText // IMPORT THIS
import com.google.firebase.database.FirebaseDatabase

class UpdateNewsActivity : AppCompatActivity() {


    private lateinit var etTitle: EditText            // XML uses <EditText>
    private lateinit var etDesc: EditText             // XML uses <EditText>
    private lateinit var etLink: TextInputEditText    // XML uses <TextInputEditText>
    private lateinit var btnUpdate: Button

    private var newsId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_news)


        etTitle = findViewById(R.id.etNewsTitle)
        etDesc = findViewById(R.id.etNewsDesc)
        etLink = findViewById(R.id.etNewsLink)
        btnUpdate = findViewById(R.id.btnUpdateNews)


        newsId = intent.getStringExtra("id")
        val oldTitle = intent.getStringExtra("title")
        val oldDesc = intent.getStringExtra("desc")
        val oldLink = intent.getStringExtra("link")


        etTitle.setText(oldTitle)
        etDesc.setText(oldDesc)
        etLink.setText(oldLink)


        btnUpdate.setOnClickListener {
            updateNews()
        }
    }

    private fun updateNews() {
        val title = etTitle.text.toString().trim()
        val desc = etDesc.text.toString().trim()
        val link = etLink.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Title and Description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (newsId != null) {
            val updateMap = mapOf(
                "title" to title,
                "description" to desc,
                "newsUrl" to link
            )

            FirebaseDatabase.getInstance().getReference("news").child(newsId!!)
                .updateChildren(updateMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "News Updated Successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Close and go back
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Error: News ID not found", Toast.LENGTH_SHORT).show()
        }
    }
}