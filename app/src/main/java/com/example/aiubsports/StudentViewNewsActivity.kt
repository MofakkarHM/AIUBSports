package com.example.aiubsports

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class StudentViewNewsActivity : AppCompatActivity() {

    private lateinit var rvNews: RecyclerView
    private lateinit var newsList: ArrayList<News>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_view_news)

        rvNews = findViewById(R.id.rvStudentNews)
        rvNews.layoutManager = LinearLayoutManager(this)

        newsList = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("news")

        getNews()
    }

    private fun getNews() {
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

                    rvNews.adapter = NewsAdapter(newsList, false)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}