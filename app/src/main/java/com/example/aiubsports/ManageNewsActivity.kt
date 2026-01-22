package com.example.aiubsports

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ManageNewsActivity : AppCompatActivity() {

    private lateinit var rvNews: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var newsList: ArrayList<News>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_news)

        rvNews = findViewById(R.id.rvNews)
        fabAdd = findViewById(R.id.fabAddNews)

        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.setHasFixedSize(true)
        newsList = arrayListOf()

        database = FirebaseDatabase.getInstance().getReference("news")

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNewsActivity::class.java))
        }

        getNewsData()
    }

    private fun getNewsData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newsList.clear()
                if (snapshot.exists()) {
                    for (newsSnapshot in snapshot.children) {
                        val news = newsSnapshot.getValue(News::class.java)
                        if (news != null) {
                            newsList.add(news)
                        }
                    }
                    newsList.reverse()


                    rvNews.adapter = NewsAdapter(newsList, true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}