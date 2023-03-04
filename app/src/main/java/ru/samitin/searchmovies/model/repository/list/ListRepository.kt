package ru.samitin.searchmovies.model.repository.list

import retrofit2.Callback
import ru.samitin.searchmovies.model.data.list.ListMovieDTO

interface ListRepository {
    fun getListMovieFromServer(idList :String,callback : Callback<ListMovieDTO>)
}