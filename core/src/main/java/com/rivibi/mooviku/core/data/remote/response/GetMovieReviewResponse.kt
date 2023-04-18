package com.rivibi.mooviku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetMovieReviewResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ReviewItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class ReviewItem(

	@field:SerializedName("author_details")
	val authorDetails: AuthorDetails,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("url")
	val url: String
)

data class AuthorDetails(

	@field:SerializedName("avatar_path")
	val avatarPath: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("username")
	val username: String
)
