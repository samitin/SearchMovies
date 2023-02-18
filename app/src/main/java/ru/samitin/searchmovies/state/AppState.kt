package ru.samitin.searchmovies.state

import ru.samitin.searchmovies.entities.Movie


sealed class AppState{
    data class Success(val movies: List<Movie>) : AppState()
    data class SuccessMovie(val movie: Movie) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
