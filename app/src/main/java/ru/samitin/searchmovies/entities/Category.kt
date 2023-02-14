package ru.samitin.searchmovies.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val categoryName : String?,
    val listMovie: List<Movie>?
): Parcelable
