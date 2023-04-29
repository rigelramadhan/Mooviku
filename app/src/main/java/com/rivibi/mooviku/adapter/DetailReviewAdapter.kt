package com.rivibi.mooviku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rivibi.mooviku.core.domain.model.Review
import com.rivibi.mooviku.core.utils.loadImage
import com.rivibi.mooviku.databinding.ItemReviewBinding

class DetailReviewAdapter(private val list: List<Review>, private val onClick: (String) -> Unit) :
    Adapter<DetailReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = list[position]

        holder.bind(review)

        holder.itemView.setOnClickListener {
            onClick(review.id)
        }
    }

    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                tvReviewName.text = review.author
                tvReviewReview.text = review.authorDetails.rating.toString()
                tvReviewComment.text = review.content
                tvReviewDate.text = review.createdAt

                imgReviewAvatar.loadImage(review.authorDetails.avatarPath)
            }
        }
    }
}