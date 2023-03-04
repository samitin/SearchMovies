package ru.samitin.searchmovies.utils

import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Category
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.data.list.Item
import ru.samitin.searchmovies.model.data.list.ListMovieDTO

fun DescriptionMovieDTO.mapToMovie(): Movie =
    Movie(
        name = this.nameRu,
        image = this.posterUrl,
        rating = this.ratingGoodReview.toString(),
        date = this.year.toString(),
        description = this.description)

fun DescriptionMovieDTO.isNotNull():Boolean{
    return !(this.nameRu == null || this.posterUrl == null ||ratingGoodReview == null ||this.year == null || this.description == null)
}
fun Item.mapToCardMovie() : CardMovie =
    CardMovie(
        id = kinopoiskId.toString(),
        name = nameRu,
        image = posterUrlPreview,
        rating = ratingKinopoisk.toString(),
        date = year.toString()
    )
fun ListMovieDTO.mapToCategory() : Category =
    Category(
        id = "1",
        categoryName = null,
        items.map { it.mapToCardMovie() }
    )
