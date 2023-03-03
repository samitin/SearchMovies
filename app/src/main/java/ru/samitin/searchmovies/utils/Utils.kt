package ru.samitin.searchmovies.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.model.data.description.DescriptionMovieDTO

fun View.showSnackBar(error:Int,reload:Int,action:(View) ->Unit){
    Snackbar.make(this,this.context.getString(error),Snackbar.LENGTH_INDEFINITE)
        .setAction(this.context.getString(reload),action).show()
}
fun View.showSnackBar(error:String,reload:Int,action:(View) ->Unit){
    Snackbar.make(this,error,Snackbar.LENGTH_INDEFINITE)
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

fun DescriptionMovieDTO.mapToMovie():Movie =
    Movie(
        name = this.name,
        image = this.poster?.url,
        rating = this.rating?.imdb.toString(),
        date = this.year.toString(),
        description = this.description)
fun DescriptionMovieDTO.isNotNull():Boolean{
    return !(this.name == null || this.poster?.url == null ||rating?.imdb == null ||this.year == null || this.description == null)
}