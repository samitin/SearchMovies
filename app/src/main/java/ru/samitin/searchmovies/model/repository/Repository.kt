package ru.samitin.searchmovies.model.repository

import ru.samitin.searchmovies.model.Movie

interface Repository {
    fun getMovieFromServer() : Movie
    fun getMovieFromLocalStorage() : Movie
}