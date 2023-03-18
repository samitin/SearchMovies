package ru.samitin.searchmovies.model.repository.room


import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE


@Dao
interface HistoryDao {
    @Insert(onConflict = IGNORE)
    fun addMovieStoryEntity(movieStoryEntity: MovieStoryEntity)

    @Insert(onConflict = IGNORE)
    fun addMovieEntity(movieEntity: MovieEntity)

    @Query("SELECT * from MovieEntity where id = :id")
    fun getMovieEntityById(id:Long):MovieEntity

    @Query("SELECT * from MovieStoryEntity where id = :id")
    fun getMovieStoryEntityById(id:Long):MovieStoryEntity

    @Query("Select * from MovieStoryEntity where isLike = :isLike")
    fun getAllMovieStoryEntityIsLike(isLike:Boolean):List<MovieStoryEntity>

    @Query("Select * from MovieStoryEntity")
    fun getAllMovieStoryEntity():List<MovieStoryEntity>

    @Query("Update MovieStoryEntity set isLike = :newIsLike where id = :id")
        fun setIslike(id:Long,newIsLike:Boolean)

    @Query("Update MovieStoryEntity set viewingTime = :newViewingTime where id = :id")
    fun setViewingTime(id:Long,newViewingTime :String)

    @Query("Update MovieStoryEntity set note = :newNote where id = :id")
    fun setNote(id:Long,newNote:String)

}