package com.example.aiubsports

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class SlotAdapter(
    private val slotList: ArrayList<Slot>,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvType: TextView = itemView.findViewById(R.id.tvSlotType)
        val tvTime: TextView = itemView.findViewById(R.id.tvSlotTime)
        val tvDate: TextView = itemView.findViewById(R.id.tvSlotDate)
        val tvStatus: TextView = itemView.findViewById(R.id.tvSlotStatus)
        val tvBookedBy: TextView = itemView.findViewById(R.id.tvBookedBy)
        val btnDelete: Button = itemView.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val currentSlot = slotList[position]

        holder.tvType.text = currentSlot.type
        holder.tvDate.text = currentSlot.date
        holder.tvTime.text = currentSlot.time
        holder.tvStatus.text = currentSlot.status

        if (currentSlot.status == "Booked") {
            holder.tvStatus.setTextColor(Color.RED)
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"))
            holder.tvBookedBy.visibility = View.VISIBLE

            holder.tvBookedBy.text = "Booked By: Loading..."

            if (!currentSlot.bookedBy.isNullOrEmpty()) {
                val userRef = FirebaseDatabase.getInstance().getReference("users").child(currentSlot.bookedBy)
                userRef.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val studentName = snapshot.child("name").value.toString()
                        holder.tvBookedBy.text = "Booked By: $studentName"
                    } else {
                        holder.tvBookedBy.text = "Booked By: Unknown User"
                    }
                }
            }
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32")) // Green
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
            holder.tvBookedBy.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditSlotActivity::class.java)
            intent.putExtra("slotId", currentSlot.slotId)
            intent.putExtra("type", currentSlot.type) // Pass type (e.g. Football)
            intent.putExtra("time", currentSlot.time)
            intent.putExtra("date", currentSlot.date)
            intent.putExtra("status", currentSlot.status)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.text = "DELETE"
        holder.btnDelete.setBackgroundColor(Color.RED)
        holder.btnDelete.setOnClickListener {
            if (currentSlot.slotId != null) {
                onDeleteClick(currentSlot.slotId)
            }
        }
    }

    override fun getItemCount(): Int {
        return slotList.size
    }
}