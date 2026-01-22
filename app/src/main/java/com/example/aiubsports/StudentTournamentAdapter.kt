package com.example.aiubsports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentTournamentAdapter(
    private val list: ArrayList<Tournament>,
    private val onJoinClick: (Tournament) -> Unit
) : RecyclerView.Adapter<StudentTournamentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvTourneyName)
        val tvSport: TextView = itemView.findViewById(R.id.tvSport)
        val btnJoin: Button = itemView.findViewById(R.id.btnJoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student_tournament, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        holder.tvSport.text = item.sportType

        holder.btnJoin.setOnClickListener {
            onJoinClick(item)
        }
    }

    override fun getItemCount() = list.size
}