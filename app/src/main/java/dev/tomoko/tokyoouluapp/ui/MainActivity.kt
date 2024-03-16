package dev.tomoko.tokyoouluapp.ui

import android.net.http.HttpException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tomoko.tokyoouluapp.ui.theme.TokyoOuluAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tomoko.tokyoouluapp.viewmodel.WeatherViewModel

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults.buttonColors
import dev.tomoko.tokyoouluapp.R
import dev.tomoko.tokyoouluapp.data.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TokyoOuluAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {

                    //getCurrentWeather(city)

                    WeatherApp()
                   // WeatherScreen()

                }
            }
        }
    }
}

@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = viewModel()) {

    val onClearButtonClick: () -> Unit = {
       weatherViewModel.clearWeatherData()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "【Weather Now!】",
            fontSize = 34.sp,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WeatherButton(text = "Tokyo") {
                weatherViewModel.fetchWeather("tokyo")
            }
            WeatherButton(text = "Oulu") {
                weatherViewModel.fetchWeather("oulu")
            }
        }

        if (weatherViewModel.isLoading) {
            LoadingScreen()
        } else {
            if (weatherViewModel.errorMessage != null) {
                Text(
                    text = weatherViewModel.errorMessage!!,
                    fontSize = 18.sp,
                )
            } else {
                Text(
                    text = weatherViewModel.cityName,
                    fontSize = 34.sp
                )
                Text(
                    text = weatherViewModel.cityWeather,
                    fontSize = 34.sp
                )
                Text(
                    text = weatherViewModel.maxTemp,
                    fontSize = 24.sp
                )
                Text(
                    text = weatherViewModel.minTemp,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick =onClearButtonClick,
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    //.fillMaxWidth()
                    .height(70.dp),
                colors = buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

            ) {
                Text(
                    text = "CLEAR",
                    fontSize = 24.sp,
                    //style = Typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun WeatherButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            //.fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp),
        colors = buttonColors(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
    )
    ) {
        Text(
            text = text,
            fontSize = 28.sp ,
        )
    }
}

