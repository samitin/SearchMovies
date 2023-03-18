package ru.samitin.searchmovies.model.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieStoryEntity(
    @PrimaryKey()
    val id :Long,
    val viewingTime:String,
    val note : String,
    val isLike :Boolean)

