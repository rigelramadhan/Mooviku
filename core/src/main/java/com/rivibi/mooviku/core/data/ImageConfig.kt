package com.rivibi.mooviku.core.data

// This config is used when calling image request from the API for minimal request error
object ImageConfig {
    const val IMAGE_URL = "https://image.tmdb.org/t/p/"
    const val SIZE_ORIGINAL = "original"

    enum class BackdropSize(val size: String) {
        W300("w300"),
        W700("w700"),
        W1280("w1280")
    }

    enum class LogoSize(val size: String) {
        W45("w45"),
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W300("w300"),
        W500("w500")
    }

    enum class PosterSize(val size: String) {
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780")
    }

    enum class ProfileSize(val size: String) {
        W45("w45"),
        W185("w185"),
        H632("h632"),
    }
}