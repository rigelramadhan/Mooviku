package com.rivibi.mooviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.rivibi.mooviku.core.domain.model.Movie
import com.rivibi.mooviku.databinding.ItemCardMovieDynamicBinding

class DynamicMovieListAdapter(private val list: List<Movie>, private val onClick: (Int) -> Unit) :
    Adapter<DynamicMovieListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardMovieDynamicBinding.inflate(
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
        holder.itemView.setOnClickListener {
            onClick(data.id)
        }
    }

    class ViewHolder(private val binding: ItemCardMovieDynamicBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: Movie) {
                binding.let {
                    it.tvMovieCardTitle.text = movie.title
                    it.tvMovieCardReleaseDate.text = movie.releaseDate

                    Glide.with(binding.root.context)
                        .load(movie.posterPath)
                        .centerCrop()
                        .into(it.imgMovieCard)
                }
            }
    }
}