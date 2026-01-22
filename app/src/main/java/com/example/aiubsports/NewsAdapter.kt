package com.example.aiubsports

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(
    private val newsList: ArrayList<News>,
    private val isAdmin: Boolean
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.title.text = currentItem.title
        holder.desc.text = currentItem.description
        holder.date.text = currentItem.date

        holder.itemView.setOnClickListener {
            if (!currentItem.newsUrl.isNullOrEmpty()) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.newsUrl))
                    holder.itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(holder.itemView.context, "Invalid Link", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (!currentItem.newsUrl.isNullOrEmpty()) {
            holder.linkText.visibility = View.VISIBLE
        } else {
            holder.linkText.visibility = View.GONE
        }


        if (isAdmin) {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnDelete.visibility = View.VISIBLE

            holder.btnEdit.setOnClickListener {
                val intent = Intent(holder.itemView.context, UpdateNewsActivity::class.java)
                intent.putExtra("id", currentItem.newsId)
                intent.putExtra("title", currentItem.title)
                intent.putExtra("desc", currentItem.description)
                intent.putExtra("link", currentItem.newsUrl)
                holder.itemView.context.startActivity(intent)
            }

            holder.btnDelete.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Hold to delete (Not implemented yet)", Toast.LENGTH_SHORT).show()
            }

        } else {
            holder.btnEdit.visibility = View.GONE
            holder.btnDelete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvNewsTitle)
        val desc: TextView = itemView.findViewById(R.id.tvNewsDesc)
        val date: TextView = itemView.findViewById(R.id.tvNewsDate)
        val linkText: TextView = itemView.findViewById(R.id.tvNewsLink)

        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditNews)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteNews)
    }
}