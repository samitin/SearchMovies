package ru.samitin.searchmovies.model.repository.home

import retrofit2.Callback
import ru.samitin.searchmovies.entities.GenreMovie
import ru.samitin.searchmovies.model.data.home.GenresAndCountriesDTO
import ru.samitin.searchmovies.model.data.list.ListMovieDTO
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource

class HomeRepositoryImpl(private val remoteDataSource: RemoteDataSource):HomeRepository {
    override fun getListMovieFromServer(genres : GenreMovie, rating : Int, callback: Callback<ListMovieDTO>) {
        remoteDataSource.getListMovie(genres.id,rating,callback)
    }
    override fun getListMovieFromServer(genres : GenreMovie, callback: Callback<ListMovieDTO>) {
        remoteDataSource.getListMovie(genres.id,callback)
    }

    override fun getGenresAndCountries(callback: Callback<GenresAndCountriesDTO>) {
        remoteDataSource.getGenresAndCountries(callback)
    }
}