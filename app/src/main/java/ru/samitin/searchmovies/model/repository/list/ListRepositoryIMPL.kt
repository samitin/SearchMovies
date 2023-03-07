package ru.samitin.searchmovies.model.repository.list

import retrofit2.Callback
import ru.samitin.searchmovies.model.data.list.ListMovieDTO
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource

class ListRepositoryIMPL(private val remoteDataSource: RemoteDataSource) : ListRepository {
    override fun getListMovieFromServer(idList: String,rating : Int, callback: Callback<ListMovieDTO>) {
        remoteDataSource.getListMovie(idList,rating,callback)
    }
    override fun getListMovieFromServer(idList: String, callback: Callback<ListMovieDTO>) {
        remoteDataSource.getListMovie(idList,callback)
    }
}