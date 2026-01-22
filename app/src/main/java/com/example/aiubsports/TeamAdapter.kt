package com.example.aiubsports

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter(
    private val teamList: ArrayList<TeamApp>,
    private val onStatusChange: (String, String) -> Unit // (AppId, NewStatus)
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvTeamName)
        val tvMembers: TextView = itemView.findViewById(R.id.tvMembers)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnApprove: Button = itemView.findViewById(R.id.btnApprove)
        val btnReject: Button = itemView.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teamList[position]

        holder.tvName.text = team.teamName
        holder.tvMembers.text = "Members: ${team.members}"
        holder.tvStatus.text = "Status: ${team.status}"
        
        if (team.status == "Approved") {
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32")) // Green
            holder.btnApprove.isEnabled = false
            holder.btnReject.isEnabled = true
        } else if (team.status == "Rejected") {
            holder.tvStatus.setTextColor(Color.RED)
            holder.btnApprove.isEnabled = true
            holder.btnReject.isEnabled = false
        } else {
            holder.tvStatus.setTextColor(Color.GRAY)
            holder.btnApprove.isEnabled = true
            holder.btnReject.isEnabled = true
        }

        holder.btnApprove.setOnClickListener {
            if (team.applicationId != null) onStatusChange(team.applicationId, "Approved")
        }

        holder.btnReject.setOnClickListener {
            if (team.applicationId != null) onStatusChange(team.applicationId, "Rejected")
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }
}