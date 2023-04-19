package com.rivibi.mooviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.databinding.ItemCardGenreBinding

class DetailGenreAdapter(private val list: List<Genres>) :
    RecyclerView.Adapter<DetailGenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = list[position]
        holder.bind(genre)
    }

    class ViewHolder(private val binding: ItemCardGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genres) {
            binding.tvGenre.text = genre.name
        }
    }
}