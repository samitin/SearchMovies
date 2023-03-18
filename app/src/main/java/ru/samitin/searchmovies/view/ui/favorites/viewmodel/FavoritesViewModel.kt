package ru.samitin.searchmovies.view.ui.favorites.viewmodel


import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.samitin.searchmovies.app.App.Companion.getHistoryDao
import ru.samitin.searchmovies.model.repository.history.LocalRepository
import ru.samitin.searchmovies.model.repository.history.LocalRepositoryIMPL
import ru.samitin.searchmovies.state.AppState

class FavoritesViewModel (val localRepository: LocalRepository = LocalRepositoryIMPL(getHistoryDao()),
                          val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()): ViewModel() {

     @SuppressLint("SuspiciousIndentation")
     fun getIsLikeMovies(){
         liveDataToObserve.value=AppState.Loading
             try {
                 liveDataToObserve.postValue(AppState.SuccessFavorites(localRepository.getAllMovieIsLike()))
             }catch (e: NullPointerException){
                 liveDataToObserve.postValue(AppState.Error(Throwable("список пуст \n $e.message")))
             }

     }
}