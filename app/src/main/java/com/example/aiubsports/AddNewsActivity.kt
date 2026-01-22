package com.example.aiubsports

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNewsActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var etLink: EditText
    private lateinit var btnPost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        etTitle = findViewById(R.id.etNewsTitle)
        etDesc = findViewById(R.id.etNewsDesc)
        etLink = findViewById(R.id.etNewsLink)
        btnPost = findViewById(R.id.btnPostNews)

        val database = FirebaseDatabase.getInstance().getReference("news")

        btnPost.setOnClickListener {

            val title = etTitle.text.toString().trim()
            val desc = etDesc.text.toString().trim()
            val link = etLink.text.toString().trim()

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                val newsId = database.push().key
                val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                if (newsId != null) {

                    val news = News(newsId, title, desc, date, link)

                    database.child(newsId).setValue(news).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "News Posted Successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter at least Title and Description", Toast.LENGTH_SHORT).show()
            }
        }
    }
}