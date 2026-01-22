package com.example.aiubsports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val userList: ArrayList<User>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvStudentName)
        val tvId: TextView = itemView.findViewById(R.id.tvStudentId)
        val tvEmail: TextView = itemView.findViewById(R.id.tvStudentEmail)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteStudent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.tvName.text = currentUser.name
        holder.tvId.text = "ID: ${currentUser.universityId}"
        holder.tvEmail.text = currentUser.email

        holder.btnDelete.setOnClickListener {
            if (currentUser.userId != null) {
                onDeleteClick(currentUser.userId)
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}