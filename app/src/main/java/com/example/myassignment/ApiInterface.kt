package com.example.myassignment

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object{
        var BASE_URL = "https://api.openweathermap.org/data/2.5/"
//        var BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}"
        var API_KEY = "b1ac3e8a3427286a6e5effafa721bd48"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

    //test cases done 2.0
    @GET("weather?")
    suspend fun getCurrentWeatherByCity(@Query("q") cityname: String,
                                @Query("appid") appid: String): Response<CurrentWeatherRes>

    @GET("weather?")
    suspend fun getCurrentWeatherByLoc(@Query("lat") lat: Double,
                                        @Query("lon") lon: Double,
                                  @Query("appid") appid: String,
                                  @Query("units") units: String): Response<CurrentWeatherRes>

}