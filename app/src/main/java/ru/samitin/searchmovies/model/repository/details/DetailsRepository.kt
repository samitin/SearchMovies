package ru.samitin.searchmovies.model.repository.details

import retrofit2.Callback
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO

interface DetailsRepository {

    fun getMovieDetailsFromServer(idMovie :Int,callback : Callback<DescriptionMovieDTO>)
}