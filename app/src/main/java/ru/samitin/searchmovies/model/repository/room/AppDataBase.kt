package ru.samitin.searchmovies.model.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [MovieStoryEntity::class,MovieEntity::class],exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getHistoryDao():HistoryDao
}