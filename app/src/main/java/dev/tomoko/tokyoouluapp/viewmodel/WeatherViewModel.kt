package dev.tomoko.tokyoouluapp.viewmodel

import android.net.http.HttpException
import android.util.Log
import androidx.lifecycle.ViewModel
import dev.tomoko.tokyoouluapp.data.model.WeatherModel
import org.json.JSONObject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import org.json.JSONException
import java.io.IOException


class WeatherViewModel : ViewModel() {

    private val weatherModel = WeatherModel(apiKey = "")

    var cityName by mutableStateOf("City Name")
        private set
    var cityWeather by mutableStateOf("City Weather")
        private set
    var maxTemp by mutableStateOf("Max Temperature")
        private set
    var minTemp by mutableStateOf("Min Temperature")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun clearWeatherData() {
        cityName = "City Name"
        cityWeather = "City Weather"
        maxTemp = "Max Temperature"
        minTemp = "Min Temperature"
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                isLoading = true // ロード中フラグをセット
                val weatherData = weatherModel.getWeather(city)
                weatherData?.let {
                    Log.d("WeatherViewModel", "Weather data fetched successfully: $weatherData")
                    weatherJsonTask(weatherData)
                } ?: run {
                    Log.e("WeatherViewModel1", "Failed to fetch weather data for city: $city")
                    errorMessage = "Failed to fetch weather data for city: $city"
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel2", "Error fetching weather data: ${e.message}")
                errorMessage = "Error fetching weather data: ${e.message}"
            } finally {
                isLoading = false // ロード中フラグを解除
            }
        }
    }
    private fun weatherJsonTask(weatherData: JSONObject) {
        Log.d("WeatherViewModel", "Parsing weather data: $weatherData") // パースされた天気データをログに記録
        try {
            val cityName = weatherData.getString("name")
            val weatherJSONArray = weatherData.getJSONArray("weather")
            val weatherJSON = weatherJSONArray.getJSONObject(0)
            val weather = weatherJSON.getString("description")
            val main = weatherData.getJSONObject("main")
            val maxTemp = "${main.getInt("temp_max") - 273}℃"
            val minTemp = "${main.getInt("temp_min") - 273}℃"

            this.cityName = cityName
            this.cityWeather = weather
            this.maxTemp = maxTemp
            this.minTemp = minTemp
        } catch (e: JSONException) {
            Log.e("WeatherViewModel3", "Error parsing weather data: ${e.message}")
        }
    }
}