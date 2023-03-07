package ru.samitin.searchmovies.model.repository.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.data.list.ListMovieDTO

interface MovieAPI {

    @GET("/api/v2.2/films/{id}")
    fun getMovieDescription(
        @Header("X-API-KEY") apiKey : String,
        @Path("id") idMovie : String
    ):Call<DescriptionMovieDTO>

    @GET("/api/v2.2/films/")
    fun getListMovie(
        @Header("X-API-KEY") apiKey : String,
        @Query("genres") genres : String,
        @Query("ratingTo") rating:Int
    ):Call<ListMovieDTO>
    @GET("/api/v2.2/films/")
    fun getListMovie(
        @Header("X-API-KEY") apiKey : String,
        @Query("genres") genres : String,
    ):Call<ListMovieDTO>

}