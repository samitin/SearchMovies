package ru.samitin.searchmovies.entities


data class GenresAndCountries(
    val countries: List<CountryMovie>,
    val genres: List<GenreMovie>
)
