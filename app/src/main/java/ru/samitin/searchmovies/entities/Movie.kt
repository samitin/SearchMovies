package ru.samitin.searchmovies.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name : String?=null,
    val image : Int = 0,
    val rating : Int?=null,
    val date : String?=null,
    val description : String?=null,
) : Parcelable
