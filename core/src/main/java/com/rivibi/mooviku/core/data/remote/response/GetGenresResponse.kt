package com.rivibi.mooviku.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetGenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
)
