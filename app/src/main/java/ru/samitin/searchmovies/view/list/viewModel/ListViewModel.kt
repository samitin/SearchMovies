package ru.samitin.searchmovies.view.list.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.samitin.searchmovies.model.data.list.ListMovieDTO
import ru.samitin.searchmovies.model.repository.list.ListRepository
import ru.samitin.searchmovies.model.repository.list.ListRepositoryIMPL
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.isNotNull
import ru.samitin.searchmovies.utils.mapToCategory

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
const val NO_RATING = -1
class ListViewModel(val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                    private val repository: ListRepository = ListRepositoryIMPL(RemoteDataSource())) : ViewModel() {

    fun getListMovieFromRemoteStorage(genres : Int,rating : Int){
        liveDataToObserve.value = AppState.Loading
        if (rating == NO_RATING)
            repository.getListMovieFromServer(genres,listMovieCallback)
        else
            repository.getListMovieFromServer(genres,rating,listMovieCallback)
    }

    private val listMovieCallback = object : Callback<ListMovieDTO> {
        override fun onResponse(call: Call<ListMovieDTO>, response: Response<ListMovieDTO>) {
            val serverResponse : ListMovieDTO?= response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null)
                    checkResponse(serverResponse)
                else
                    AppState.Error(Throwable(response.errorBody()?.string() ?: SERVER_ERROR))
            )
        }

        override fun onFailure(call: Call<ListMovieDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse(listMovieDTO: ListMovieDTO):AppState{
        return if (!listMovieDTO.items.isNullOrEmpty())
            AppState.Success(listMovieDTO.mapToCategory())
        else
            AppState.Error(Throwable(CORRUPTED_DATA))
    }
}

