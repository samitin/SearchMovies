package ru.samitin.searchmovies.model.repository

import ru.samitin.searchmovies.entities.Category
import ru.samitin.searchmovies.entities.Movie

interface Repository {
    fun getMovieDescriptionFromServer(id : String,loaded:(movie:Movie)->Unit,failed:(err:Throwable)->Unit)
    fun getMoviesFromLocalStorage(): List<Movie>

    fun getMoviesFromServer(): List<Movie>
}
