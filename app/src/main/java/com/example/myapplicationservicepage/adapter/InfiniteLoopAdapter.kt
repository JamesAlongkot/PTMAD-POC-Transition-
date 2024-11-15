package com.example.myapplicationservicepage.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationservicepage.R

class InfiniteLoopAdapter(private val items: List<Int>) : RecyclerView.Adapter<InfiniteLoopAdapter.ViewHolder>() {

    // Total item count to create an illusion of infinite scroll
    private val loopedItemCount = items.size
//    private val loopedItemCount = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Modulo operator to simulate infinite loop by reusing the original list items
        val actualPosition = position % items.size
        holder.bind(items[actualPosition])
    }

    override fun getItemCount(): Int {
        // Return a large number to simulate infinite scrolling
        return loopedItemCount
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view: ImageView = itemView.findViewById(R.id.item_icon)

        fun bind(img: Int) {
            view.setImageResource(img)
        }
    }
}
