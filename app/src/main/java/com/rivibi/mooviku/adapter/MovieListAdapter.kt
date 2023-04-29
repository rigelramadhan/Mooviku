package com.rivibi.mooviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.core.utils.loadImage
import com.rivibi.mooviku.databinding.ItemCardMovieBinding

class MovieListAdapter(private val list: List<Movie>, private val onClick: (Int) -> Unit) : Adapter<MovieListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)

        holder.itemView.setOnClickListener { onClick(data.id) }
    }

    inner class ViewHolder(private val binding: ItemCardMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.let {
                it.tvMovieCardTitle.text = movie.title
                it.tvMovieCardReleaseDate.text = movie.releaseDate

                it.imgMovieCard.loadImage(movie.posterPath)
            }
        }
    }
}