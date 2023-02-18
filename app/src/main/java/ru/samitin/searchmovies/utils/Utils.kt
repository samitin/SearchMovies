package ru.samitin.searchmovies.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO

fun View.showSnackBar(error:Int,reload:Int,action:(View) ->Unit){
    Snackbar.make(this,this.context.getString(error),Snackbar.LENGTH_INDEFINITE)
        .setAction(this.context.getString(reload),action).show()
}
fun View.show(): View{
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
    return this
}
fun View.hide(): View{
    if (visibility != View.GONE)
        visibility = View.GONE
    return this
}

fun mapDescriptionMovieDTOtoMovie(descriptionMovieDTO: DescriptionMovieDTO):Movie =
    Movie(
        name = descriptionMovieDTO.title,
        image = descriptionMovieDTO.image,
        rating = descriptionMovieDTO.imDbRating,
        date = descriptionMovieDTO.releaseDate,
        description = descriptionMovieDTO.plotLocal)