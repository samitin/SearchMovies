package ru.samitin.searchmovies.view.details.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.samitin.searchmovies.model.repository.Repository
import ru.samitin.searchmovies.model.repository.RepositoryImpl
import ru.samitin.searchmovies.state.AppState

class DetailsViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                       private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() : LiveData<AppState> = liveDataToObserve

    fun getMovieFromLocalStorage() = getDataFromLocalSource()
    fun getMovieFromRemoteStorage() = getDataFromLocalSource()
    private fun getDataFromLocalSource(){
        liveDataToObserve.value = AppState.Loading
        Thread{
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getMoviesFromLocalStorage()))
        }.start()
    }
}