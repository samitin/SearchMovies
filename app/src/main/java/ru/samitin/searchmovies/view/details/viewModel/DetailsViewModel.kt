package ru.samitin.searchmovies.view.details.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.repository.details.DetailsRepository
import ru.samitin.searchmovies.model.repository.details.DetailsRepositoryIMPL
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.isNotNull
import ru.samitin.searchmovies.utils.mapToMovie

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class DetailsViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                       private val repository: DetailsRepository = DetailsRepositoryIMPL(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() : LiveData<AppState> = liveDataToObserve

    fun getDataFromServer(id:String){
        liveDataToObserve.value = AppState.Loading

        repository.getMovieDetailsFromServer(id,desCallback)


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
       return if (descriptionMovieDTO.isNotNull())
                   AppState.SuccessMovie(descriptionMovieDTO.mapToMovie())
               else
                   AppState.Error(Throwable(CORRUPTED_DATA))
    }
}