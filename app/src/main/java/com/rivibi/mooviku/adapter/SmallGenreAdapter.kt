package com.rivibi.mooviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivibi.mooviku.core.domain.model.Genres
import com.rivibi.mooviku.databinding.ItemCardGenreBinding

class SmallGenreAdapter(private val list: List<Genres>, private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<SmallGenreAdapter.ViewHolder>() {

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
        holder.bind(genre, onClick)
    }

    class ViewHolder(private val binding: ItemCardGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genres, onClick: (Int) -> Unit) {
            binding.tvGenre.text = genre.name
            binding.tvGenre.setOnClickListener { onClick(genre.id) }
        }
    }
}