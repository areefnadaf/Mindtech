package com.example.mindtech.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mindtech.R
import java.util.*

class ViewPagerAdapter(list: List<String>?) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    private val list = list

    class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = itemView.findViewById(R.id.itemText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_pager, parent, false
        )
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.textView.setText(list?.get(position))
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.itemView.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }
}