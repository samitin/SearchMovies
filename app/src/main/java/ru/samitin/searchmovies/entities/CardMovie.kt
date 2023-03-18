package ru.samitin.searchmovies.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class CardMovie(
     val id : Int ?=null,
     val name : String?=null,
     val image : String ?= null,
     val rating : Double?=null,
     val date : String?=null, ) : Parcelable

