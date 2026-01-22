package com.example.aiubsports

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentSlotAdapter(
    private val list: ArrayList<Slot>,
    private val onBookClick: (Slot) -> Unit
) : RecyclerView.Adapter<StudentSlotAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvType: TextView = itemView.findViewById(R.id.tvSlotType)
        val tvTime: TextView = itemView.findViewById(R.id.tvSlotTime)
        val tvStatus: TextView = itemView.findViewById(R.id.tvSlotStatus)
        val btnAction: Button = itemView.findViewById(R.id.btnAction) // We reuse the same ID for simplicity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvType.text = item.type
        holder.tvTime.text = "${item.date} at ${item.time}"
        holder.tvStatus.text = item.status

        if (item.status == "Available") {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50")) // Green
            holder.btnAction.text = "BOOK NOW"
            holder.btnAction.isEnabled = true
            holder.btnAction.setBackgroundColor(Color.parseColor("#0D47A1")) // Blue Button

            holder.btnAction.setOnClickListener {
                onBookClick(item)
            }
        } else {
            holder.tvStatus.setTextColor(Color.RED)
            holder.btnAction.text = "BOOKED"
            holder.btnAction.isEnabled = false // Disable button
            holder.btnAction.setBackgroundColor(Color.GRAY)
        }
    }

    override fun getItemCount() = list.size
}