package com.example.myassignment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentWeatherRes(
    val coord:Coord,
    val weather:ArrayList<Weather>,
    val base:String,
    val main:Main,
    val visibility:String,
    val wind:Wind,
    val clouds:Clouds,
    val dt:Long,
    val sys:Sys,
    val timezone:Long,
    val id:Long,
    val name:String,
    val message:String,
    val cod:Int,

):Parcelable

@Parcelize
data class Coord(
    val lon:Double,
    val lat:Double
):Parcelable

@Parcelize
data class Weather(
    val id:Long,
    val main:String,
    val description:String,
    val icon:String,
):Parcelable

@Parcelize
data class Main(
    val temp:Float,
    val feels_like:Float,
    val temp_min:Float,
    val temp_max:Float,
    val pressure:Int,
    val humidity:Int,
    val sea_level:Int,
    val grnd_level:Int,

):Parcelable

@Parcelize
data class Wind(
    val speed:Float,
    val deg:Int,
    val gust:Float,

):Parcelable

@Parcelize
data class Clouds(
    val all:Int
):Parcelable

@Parcelize
data class Sys(
    val type:Int,
    val id:Int,
    val country:String,
    val sunrise:Long,
    val sunset:Long
):Parcelable







