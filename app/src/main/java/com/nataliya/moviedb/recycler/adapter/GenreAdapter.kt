package com.nataliya.moviedb.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nataliya.moviedb.R
import com.nataliya.moviedb.model.MovieGenre

class GenreAdapter(
    private var list: ArrayList<MovieGenre>
) : RecyclerView.Adapter<GenreAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_genre,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)

        fun bind(data: MovieGenre) {
            tvName.text = data.name
        }
    }
}