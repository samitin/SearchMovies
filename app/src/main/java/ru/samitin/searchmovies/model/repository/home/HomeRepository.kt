package ru.samitin.searchmovies.model.repository.home

import retrofit2.Callback
import ru.samitin.searchmovies.entities.GenreMovie
import ru.samitin.searchmovies.model.data.home.GenresAndCountriesDTO
import ru.samitin.searchmovies.model.data.list.ListMovieDTO

interface HomeRepository {
    fun getListMovieFromServer(genres : GenreMovie, rating : Int, callback: Callback<ListMovieDTO>)
    fun getListMovieFromServer(genres : GenreMovie, callback: Callback<ListMovieDTO>)
    fun getGenresAndCountries(callback: Callback<GenresAndCountriesDTO>)
}