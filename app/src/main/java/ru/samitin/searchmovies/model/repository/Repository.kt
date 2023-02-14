package ru.samitin.searchmovies.model.repository

import ru.samitin.searchmovies.entities.Category
import ru.samitin.searchmovies.entities.Movie

interface Repository {
    fun getMoviesFromLocalStorage(): List<Movie>

    fun getMoviesFromServer(): List<Movie>
}
