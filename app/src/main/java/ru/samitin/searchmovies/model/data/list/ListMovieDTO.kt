package ru.samitin.searchmovies.model.data.list

data class ListMovieDTO(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)