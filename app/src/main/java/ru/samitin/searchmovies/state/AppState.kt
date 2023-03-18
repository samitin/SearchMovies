package ru.samitin.searchmovies.state

import ru.samitin.searchmovies.entities.*


sealed class AppState{
    data class SuccessFavorites(val listCardMovie:List<CardMovie>):AppState()
    data class SuccessGenresAndCountries(val genresAndCountries: GenresAndCountries ) : AppState()
    data class Success(val movies: Category) : AppState()
    data class SuccessMovie(val movie: Movie) : AppState()
    data class Story(val story:MovieStory):AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
