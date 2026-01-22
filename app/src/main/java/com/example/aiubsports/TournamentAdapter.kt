package com.example.aiubsports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TournamentAdapter(
    private val list: ArrayList<Tournament>,
    private val onViewTeamsClick: (String, String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<TournamentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvTourneyName)
        val tvSport: TextView = itemView.findViewById(R.id.tvSport)
        val btnView: Button = itemView.findViewById(R.id.btnViewTeams)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteTournament)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.name
        holder.tvSport.text = item.sportType

        // 1. View Teams Logic
        holder.btnView.setOnClickListener {
            if (item.tournamentId != null) {
                onViewTeamsClick(item.tournamentId, item.name ?: "")
            }
        }

        holder.btnDelete.setOnClickListener {
            if (item.tournamentId != null) {
                onDeleteClick(item.tournamentId)
            }
        }
    }

    override fun getItemCount() = list.size
}