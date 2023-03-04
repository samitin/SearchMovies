package ru.samitin.searchmovies.model.repository.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.samitin.searchmovies.BuildConfig
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.data.list.ListMovieDTO

class RemoteDataSource {

    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(MovieAPI::class.java)

    fun getMovieDetails(idMovie : String , callback:Callback<DescriptionMovieDTO>){
        movieAPI.getMovieDescription(BuildConfig.API_KEY_KINOPOISK,idMovie).enqueue(callback)
    }

    fun getListMovie(genres : String , callback:Callback<ListMovieDTO>){
        movieAPI.getListMovie(BuildConfig.API_KEY_KINOPOISK,genres).enqueue(callback)
    }
}