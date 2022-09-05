package com.example.mindtech.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mindtech.R
import com.example.mindtech.datamodels.Movie
import java.util.*
import java.util.stream.Stream

class CustomAdapter(private val dataset: List<Movie>?) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(), Filterable {

    var filteredDataset: List<Movie>? = dataset

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ViewHolder(view)


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun solution(numbers: MutableList<String>): Boolean {

        var numbers1: List<String> = numbers

        if (numbers.size == 1) {

            return true
        }


        for (i = 0; i < numbers.size;i++) {


            var b = numbers.asSequence().filter { s -> s.startsWith(num) }

        }


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = filteredDataset?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return if (filteredDataset != null) filteredDataset!!.size else 0
    }

    override fun getFilter(): Filter {
        println("Get Filter Called")
        return exampleFilter
    }

    private var exampleFilter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val charString = charSequence.toString()
            println("Char String " + charString)
            if (charString.isEmpty()) {
                filteredDataset = dataset
            } else {
                val filteredList: MutableList<Movie> = ArrayList()
                for (movie in dataset!!) {
                    if (movie.title.toLowerCase()
                            .contains(charString.lowercase(Locale.getDefault()))
                    ) {
                        filteredList.add(movie)
                    }
                }
                filteredDataset = filteredList
            }
            val filterResults = FilterResults()
            filterResults.values = filteredDataset
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            println("Publish Results")
            filteredDataset = filterResults.values as List<Movie>

            notifyDataSetChanged()
        }
    }

}