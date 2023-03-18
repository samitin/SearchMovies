package ru.samitin.searchmovies.view.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.samitin.searchmovies.entities.GenreMovie
import ru.samitin.searchmovies.model.data.home.GenresAndCountriesDTO
import ru.samitin.searchmovies.model.data.list.ListMovieDTO
import ru.samitin.searchmovies.model.repository.home.HomeRepository
import ru.samitin.searchmovies.model.repository.home.HomeRepositoryImpl
import ru.samitin.searchmovies.model.repository.list.ListRepository
import ru.samitin.searchmovies.model.repository.list.ListRepositoryIMPL
import ru.samitin.searchmovies.model.repository.retrofit.RemoteDataSource
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.mapToCategory

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
const val NO_RATING = -1

class HomeViewModel (val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                     private val repository: HomeRepository = HomeRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getGenresAndCountries(){
        liveDataToObserve.value = AppState.Loading
        repository.getGenresAndCountries(genresAndCountriesCallback)
    }

    fun getListMovieFromRemoteStorage(genres : GenreMovie,rating : Int = NO_RATING){
        liveDataToObserve.value = AppState.Loading
        if (rating == NO_RATING)
            repository.getListMovieFromServer(genres,listMovieCallback(genres))
        else
            repository.getListMovieFromServer(genres,rating,listMovieCallback(genres))
    }
    private val genresAndCountriesCallback = object : Callback<GenresAndCountriesDTO>{
        override fun onResponse(call: Call<GenresAndCountriesDTO>, response: Response<GenresAndCountriesDTO>) {
            val serverResponse : GenresAndCountriesDTO?= response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null)
                    checkResponse(serverResponse)
                else
                    error(response.code(),response.message())
            )
        }
        override fun onFailure(call: Call<GenresAndCountriesDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message?: REQUEST_ERROR)))
        }
    }

    private fun listMovieCallback(genres:GenreMovie) = object : Callback<ListMovieDTO> {
        override fun onResponse(call: Call<ListMovieDTO>, response: Response<ListMovieDTO>) {
            val serverResponse : ListMovieDTO?= response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null)
                    checkResponse(serverResponse,genres)
                else error(response.code(),response.message())
            )
        }

        override fun onFailure(call: Call<ListMovieDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message?: REQUEST_ERROR)))
        }

    }


    private fun checkResponse(listMovieDTO: ListMovieDTO, genres:GenreMovie):AppState{
        return if (!listMovieDTO.items.isNullOrEmpty())
            AppState.Success(listMovieDTO.mapToCategory(genres))
        else
            AppState.Error(Throwable(CORRUPTED_DATA))
    }

    private fun checkResponse(genresAndCountriesDTO: GenresAndCountriesDTO):AppState{
        return if (!genresAndCountriesDTO.genres.isNullOrEmpty()||genresAndCountriesDTO.countries.isNullOrEmpty())
            AppState.SuccessGenresAndCountries(genresAndCountriesDTO.mapToGenresAndCountries())
        else
            AppState.Error(Throwable(CORRUPTED_DATA))
    }
    fun error(error: Int,massage:String):AppState.Error=
        when(error){
            401 ->AppState.Error(Throwable("Пустой или неправильный токен \n $massage"))
            402 ->AppState.Error(Throwable("Превышен лимит запросов(или дневной, или общий) \n $massage"))
            429 ->AppState.Error(Throwable("Слишком много запросов. Общий лимит - 20 запросов в секунду \n $massage"))
            else -> {AppState.Error(Throwable(massage?: REQUEST_ERROR))}
        }

}