package com.example.myassignment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var viewModel: WeatherViewModel
    private val apiservice = ApiInterface.create()



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        setTransparentStatusBar()
        setContentView(R.layout.activity_main)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(apiservice))).get(WeatherViewModel::class.java)



        btn_location?.setOnClickListener {
            checkLocation()
        }

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setTransparentStatusBar() {
        window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            statusBarColor = Color.TRANSPARENT

        }
    }
    private fun checkLocation(){
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }else{
            obtainLocation()
        }
    }
    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        Log.e("lat", "function")
        // get the last location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // get the latitude and longitude
                // and create the http URL
//                weather_url1 = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&key=" + api_id1
//                Log.e("lat", weather_url1.toString())
                Log.e("lat", location?.latitude.toString())
                Log.e("lat", location?.longitude.toString())
                txt_msg.text = "LAT: ${location?.latitude.toString()} \nLon: ${location?.longitude.toString()}"
                // this function will
                // fetch data from URL
//                getWeather("Nashik")
                getWeather(location?.latitude!!, location?.longitude!!)
            }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    fun getWeather(city: String){
        viewModel.fetchCurrentWeather(city)
        viewModel.result_observe.observe(this, Observer { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    txt_msg.text = "${txt_msg.text.toString()} \nRes: ${it.data?.name}"
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
                Status.NETWORK_ERROR -> {

                }
                Status.SESSION_EXPIRY -> {

                }

            }
        })

    }
    fun getWeather(lat:Double, lon:Double){
        viewModel.fetchCurrentWeather(lat,lon)
        viewModel.result_observe.observe(this, Observer { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    txt_msg.text = "${txt_msg.text.toString()} \nRes: ${it.data?.name}\n${it.data?.weather?.get(0)?.main}"
                    txt_loc_name.text = "${it.data?.name}, ${it.data?.sys?.country}"
                    txt_status.text = "${it.data?.weather?.get(0)?.description}"
                    txt_temp.text = "${it.data?.main?.temp} °C"
                    txt_temp_min.text = "Min Temp: ${it.data?.main?.temp_min} °C"
                    txt_temp_max.text = "Max Temp: ${it.data?.main?.temp_max} °C"

                    txt_sunrise.text = "${SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.data?.sys?.sunrise!! * 1000))}"
                    txt_sunset.text = "${SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.data?.sys?.sunset!! * 1000))}"
                    txt_wind.text = "${it.data?.wind?.speed}"
                    txt_pressure.text = "${it.data?.main?.pressure}"
                    txt_humidity.text = "${it.data?.main?.humidity}"

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
                Status.NETWORK_ERROR -> {

                }
                Status.SESSION_EXPIRY -> {

                }

            }
        })

    }

}