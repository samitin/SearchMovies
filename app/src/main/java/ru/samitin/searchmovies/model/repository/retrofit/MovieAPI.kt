package ru.samitin.searchmovies.model.repository.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO

interface MovieAPI {

    @GET("/v1/movie/{id}")
    fun getMovieDescription(
        @Header("X-API-KEY") apiKey : String,
        @Path("id") idMovie : String
    ):Call<DescriptionMovieDTO>
}