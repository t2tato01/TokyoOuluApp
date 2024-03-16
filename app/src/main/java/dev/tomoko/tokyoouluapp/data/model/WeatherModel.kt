package dev.tomoko.tokyoouluapp.data.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class WeatherModel(private val apiKey: String) {

        suspend fun getWeather(city: String): JSONObject? {
            return withContext(Dispatchers.IO) {
                //lang=ja
                val mainUrl = "https://api.openweathermap.org/data/2.5/weather?"
                val weatherUrl = "$mainUrl&q=$city&appid=$apiKey"

                try {
                    val urlObj = URL(weatherUrl)
                    val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                    val response = br.readText()
                    JSONObject(response)
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
}
