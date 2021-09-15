package com.example.myassignment

class MainRepository constructor(private val service: ApiInterface)  {

    suspend fun getWeatherByCity(city:String, key:String)= service.getCurrentWeatherByCity(city, key)
    suspend fun getWeatherByLoc(lat:Double, lon:Double, key:String)= service.getCurrentWeatherByLoc(lat, lon, key, "Metric")
}