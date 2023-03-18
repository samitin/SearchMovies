package ru.samitin.searchmovies.model.repository.details

import retrofit2.Callback
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource

class DetailsRepositoryIMPL(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(idMovie: Int, callback: Callback<DescriptionMovieDTO>) {
        remoteDataSource.getMovieDetails(idMovie,callback)
    }
}