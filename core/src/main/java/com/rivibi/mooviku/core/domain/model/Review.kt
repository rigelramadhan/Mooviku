package com.rivibi.mooviku.core.domain.model

data class Review(
    val authorDetails: AuthorDetails,
    val updatedAt: String,
    val author: String,
    val createdAt: String,
    val id: String,
    val content: String,
    val url: String
)

data class AuthorDetails(
    val avatarPath: String,
    val name: String,
    val rating: Double,
    val username: String
)