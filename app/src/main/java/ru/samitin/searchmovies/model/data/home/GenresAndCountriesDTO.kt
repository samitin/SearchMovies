package ru.samitin.searchmovies.model.data.home

import ru.samitin.searchmovies.entities.CountryMovie
import ru.samitin.searchmovies.entities.GenreMovie
import ru.samitin.searchmovies.entities.GenresAndCountries

data class GenresAndCountriesDTO(
    val countries: List<Country>,
    val genres: List<Genre>
){
    fun mapToGenresAndCountries()=GenresAndCountries(
        countries = countries.map { CountryMovie(it.country,it.id) },
        genres = genres.map { GenreMovie(it.genre,it.id) }
    )
}