package ru.samitin.searchmovies.model.repository.history

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.entities.MovieStory
import ru.samitin.searchmovies.model.repository.room.MovieEntity
import ru.samitin.searchmovies.model.repository.room.MovieStoryEntity

interface LocalRepository {

    fun addMovieStory(movieStory: MovieStory)


    fun getMovieStoryById(id:Int): MovieStory


    fun getAllMovieStoryIsLike():List<MovieStory>


    fun getAllMovieStory():List<MovieStory>


    fun setIslike(id:Int,newIsLike:Boolean)


    fun setViewingTime(id:Int,newViewingTime :String)

    fun setNote(id:Int,newNote:String)


    fun addMovie(movieEntity: Movie)

    fun getMovieById(id:Long):Movie

    fun getAllMovieIsLike():List<CardMovie>
}