package ru.samitin.searchmovies.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name : String?=null,
    val image : String ?= null,
    val rating : String?=null,
    val date : String?=null,
    val description : String?=null,
) : Parcelable
