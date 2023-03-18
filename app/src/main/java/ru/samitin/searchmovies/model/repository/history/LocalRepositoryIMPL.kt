package ru.samitin.searchmovies.model.repository.history


import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.entities.MovieStory
import ru.samitin.searchmovies.model.repository.room.HistoryDao
import ru.samitin.searchmovies.model.repository.room.MovieEntity
import ru.samitin.searchmovies.utils.mapTOMovieStory
import ru.samitin.searchmovies.utils.mapToMovieStoryEntity

class LocalRepositoryIMPL(private val historyDao: HistoryDao):LocalRepository {
    override fun addMovieStory(movieStory: MovieStory) {
        historyDao.addMovieStoryEntity(movieStory.mapToMovieStoryEntity())
    }

    override fun getMovieStoryById(id: Int) =
        historyDao.getMovieStoryEntityById(id.toLong()).mapTOMovieStory()

    override fun getAllMovieStoryIsLike() =
        historyDao.getAllMovieStoryEntityIsLike(true).map { it.mapTOMovieStory() }

    override fun getAllMovieStory() =
        historyDao.getAllMovieStoryEntity().map { it.mapTOMovieStory() }

    override fun setIslike(id: Int, newIsLike: Boolean) {
        historyDao.setIslike(id.toLong(),newIsLike)
    }

    override fun setViewingTime(id: Int, newViewingTime: String) {
        historyDao.setViewingTime(id.toLong(),newViewingTime)
    }

    override fun setNote(id: Int, newNote: String) {
        historyDao.setNote(id.toLong(),newNote)
    }

    override fun addMovie(movie: Movie) {
        historyDao.addMovieEntity(MovieEntity(movie.id.toLong(),movie.name ?:"no name",movie.rating ?: 0.0,movie.image?:"",movie.date?:"no date",movie.description?:"no description"))
    }

    override fun getMovieById(id: Long): Movie {
        return historyDao.getMovieEntityById(id).toMovie()
    }

    override fun getAllMovieIsLike(): List<CardMovie> {
       return historyDao.getAllMovieStoryEntityIsLike(true).map {
            historyDao.getMovieEntityById(it.id).toCardMovie()
        }
    }


}