package com.rivibi.mooviku.core.data.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(
        val errorCode: ApiResponseMethod? = null,
        val errorMessage: String = "An error has occurred"
    ) : ApiResponse<Nothing>()

    object Empty : ApiResponse<Nothing>()
}
