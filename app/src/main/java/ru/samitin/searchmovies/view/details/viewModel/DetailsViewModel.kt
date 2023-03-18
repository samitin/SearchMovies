package ru.samitin.searchmovies.view.details.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.samitin.searchmovies.app.App
import ru.samitin.searchmovies.entities.MovieStory
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.repository.details.DetailsRepository
import ru.samitin.searchmovies.model.repository.details.DetailsRepositoryIMPL
import ru.samitin.searchmovies.model.repository.history.LocalRepository
import ru.samitin.searchmovies.model.repository.history.LocalRepositoryIMPL
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.isNotNull
import ru.samitin.searchmovies.utils.mapToMovie
import java.util.Date

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class DetailsViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                       val localRepository: LocalRepository = LocalRepositoryIMPL(App.getHistoryDao()),
                       private val repository: DetailsRepository = DetailsRepositoryIMPL(RemoteDataSource())
) : ViewModel() {
    private var id:Int ?=null
    fun getLiveData() : LiveData<AppState> = liveDataToObserve

    fun getDataFromServer(id:Int){
        this.id = id
        liveDataToObserve.value = AppState.Loading
        repository.getMovieDetailsFromServer(id,desCallback)
    }
    fun getDataStory(id:Int){
        setMovieStory(MovieStory(id,Date().toString(),"",false))
        liveDataToObserve.postValue(AppState.Story(localRepository.getMovieStoryById(id)))
    }
    private fun setMovieStory(story:MovieStory){
        localRepository.addMovieStory(story)
    }

    @SuppressLint("SuspiciousIndentation")
    fun saveLike(isLike:Boolean){
        if (id!=null){
            localRepository.setIslike(id!!,isLike)
            liveDataToObserve.postValue(AppState.Story(localRepository.getMovieStoryById(id!!)))
        }

    }
    fun saveDate(date: Date){
        if (id!=null){
            localRepository.setViewingTime(id!!,date.toString())
            liveDataToObserve.postValue(AppState.Story(localRepository.getMovieStoryById(id!!)))
        }

    }
    @SuppressLint("SuspiciousIndentation")
    fun saveNote(note:String){
        if (id!=null){
            localRepository.setNote(id!!,note)
            liveDataToObserve.postValue(AppState.Story(localRepository.getMovieStoryById(id!!)))
        }

    }



    private val desCallback = object : Callback<DescriptionMovieDTO>{
        override fun onResponse(call: Call<DescriptionMovieDTO>, response: Response<DescriptionMovieDTO>) {
            val serverResponse :DescriptionMovieDTO ?= response.body()
            liveDataToObserve.postValue(
                    if (response.isSuccessful && serverResponse != null)
                        checkResponse(serverResponse)
                    else
                        AppState.Error(Throwable(response.errorBody()?.string() ?: SERVER_ERROR))
            )
        }

        override fun onFailure(call: Call<DescriptionMovieDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse(descriptionMovieDTO: DescriptionMovieDTO):AppState{
       return if (descriptionMovieDTO.isNotNull()){
           val movie = descriptionMovieDTO.mapToMovie()
           localRepository.addMovie(movie)
           AppState.SuccessMovie(movie)
       } else
           AppState.Error(Throwable(CORRUPTED_DATA))
    }
}