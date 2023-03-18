package ru.samitin.searchmovies.utils

import ru.samitin.searchmovies.entities.*
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO
import ru.samitin.searchmovies.model.data.list.Item
import ru.samitin.searchmovies.model.data.list.ListMovieDTO
import ru.samitin.searchmovies.model.repository.room.MovieStoryEntity

fun DescriptionMovieDTO.mapToMovie(): Movie =
    Movie(
        id = this.kinopoiskId,
        name = this.nameRu,
        image = this.posterUrl,
        rating = this.ratingGoodReview,
        date = this.year.toString(),
        description = this.description)

fun DescriptionMovieDTO.isNotNull(): Boolean =
    !(this.posterUrl == null && ratingGoodReview == null && this.year == null && this.description == null)
fun Item.mapToCardMovie() : CardMovie =
    CardMovie(
        id = kinopoiskId,
        name = nameRu,
        image = posterUrlPreview,
        rating = ratingKinopoisk,
        date = year.toString()
    )
fun ListMovieDTO.mapToCategory(id:Int = 1,categoryName:String = "") : Category =
    Category(
        id.toString(),
        categoryName,
        items.map { it.mapToCardMovie() }
    )

fun ListMovieDTO.mapToCategory(genres: GenreMovie) : Category =
    Category(
        genres.id.toString(),
        genres.genre,
        items.map { it.mapToCardMovie() }
    )
fun MovieStory.mapToMovieStoryEntity()=MovieStoryEntity(id.toLong(),viewingTime,note,isLike)
fun MovieStoryEntity.mapTOMovieStory() = MovieStory(id.toInt(),viewingTime,note,isLike)

