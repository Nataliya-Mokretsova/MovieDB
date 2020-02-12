package com.nataliya.moviedb.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.nataliya.moviedb.R
import com.nataliya.moviedb.model.Movie

class MovieAdapter(
    private var list: ArrayList<Movie>, private var onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
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

    fun setList(list: ArrayList<Movie>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        private val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvLanguage: TextView = itemView.findViewById(R.id.tvLanguage)

        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        private val ivContent: ImageView = itemView.findViewById(R.id.ivPicture)

        fun bind(data: Movie) {
            val date = "Release Date: ${data.getReleaseDate(data.releaseDate)}"
            tvDate.text = date

            tvName.text = data.title
            tvOverview.text = data.overview
            tvLanguage.text = data.originalLanguage
            tvRating.text = data.voteAverage.toString()

            val stars = if (data.voteAverage != 0.0f) 0.5f * (1 + data.voteAverage) else 0f
            ratingBar.rating = stars

            val options =
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .transform(RoundedCorners(16))

            Glide.with(ivContent.context)
                .load(data.getPosterPath(data.posterPath))
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade())
                .into(ivContent)

            itemView.setOnClickListener { onMovieClickListener.onMovieClick(data.id) }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(id: Int)
    }
}