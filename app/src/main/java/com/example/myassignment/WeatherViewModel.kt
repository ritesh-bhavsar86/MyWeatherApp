package com.example.myassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch

class WeatherViewModel(private val mainRepository: MainRepository): ViewModel() {

    private val result = MutableLiveData<Resource<CurrentWeatherRes>>()
    lateinit var result_observe: LiveData<Resource<CurrentWeatherRes>>



    fun fetchCurrentWeather(city : String){
        result_observe = result
        viewModelScope.launch {
            result.postValue(Resource.loading(null))
                mainRepository.getWeatherByCity(city, ApiInterface.API_KEY).let {
                    if(it.isSuccessful){
                        if(it.body()!!.cod==200){
                            result.postValue(Resource.success(it.body()))
                        }else {
                            result.postValue(Resource.error(it.body()!!.message, null))
                        }
                    } else{
                        if(it.errorBody()!=null){
                            println(Gson().toJson(it.errorBody()!!.charStream()))
                        }
                    }
                }
        }
    }
    fun fetchCurrentWeather(lat:Double, lon:Double){
        result_observe = result
        viewModelScope.launch {
            result.postValue(Resource.loading(null))
            mainRepository.getWeatherByLoc(lat, lon, ApiInterface.API_KEY).let {
                if(it.isSuccessful){
                    if(it.body()!!.cod==200){
                        result.postValue(Resource.success(it.body()))
                    }else {
                        result.postValue(Resource.error(it.body()!!.message, null))
                    }
                } else{
                    if(it.errorBody()!=null){
                        println(Gson().toJson(it.errorBody()!!.charStream()))
                    }
                }
            }
        }
    }

}