package ru.samitin.searchmovies.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id : String?,
    val categoryName : String?,
    val listMovie: List<CardMovie>?
): Parcelable
