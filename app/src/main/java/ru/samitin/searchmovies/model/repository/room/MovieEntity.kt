package ru.samitin.searchmovies.model.repository.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.entities.MovieStory

//indices = индексы , Index = индекс ,unique = уникальный
//Entity = Сущность, PrimaryKey =  Основной ключ
@Entity()
data class MovieEntity(
    @PrimaryKey()
    val id :Long,
    val name : String,
    val rating :Double,
    val img:String,
    val date : String,
    val description:String,
){
    @Ignore
    fun toMovie() = Movie(id = id.toInt(), name = name,image = img,rating= rating,date = date, description = description)
    @Ignore
    fun toCardMovie() = CardMovie(id = id.toInt(), name = name,image = img,rating= rating,date = date,)
}

