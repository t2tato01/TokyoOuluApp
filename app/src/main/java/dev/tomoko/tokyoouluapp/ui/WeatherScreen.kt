package dev.tomoko.tokyoouluapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import dev.tomoko.tokyoouluapp.data.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun WeatherScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // 現在の天気を取得するための関数
    val getCurrentWeather: () -> Unit = {
        // RetrofitInstance.api.getCurrentWeather の呼び出しをバックグラウンドで行う
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(
                    "tokyo",
                    "metric",
                    "41ac77b7f759a36017e8b177d86c001c"
                )
                if (response.isSuccessful && response.body() != null) {
                    val weather = response.body()!!.weather
                    // UIスレッドでの更新を行う
                    lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        // UIの表示を更新
                        // ここでweatherを適切なUI要素にセットする
                        // 例えば、Textで表示する場合は、Text(weather)など
                    }
                }
            } catch (e: IOException) {
                // 通信エラー時の処理
            }
        }
    }

    Column {
        // 天気情報を取得するボタン
        Button(onClick = getCurrentWeather) {
            Text("Get Current Weather")
        }

        // 以下に天気情報を表示するUIを追加する
    }
}

/*
private fun getCurrentWeather(city: String){
    GlobalScope.launch(Dispatchers.IO) {
        val response = try {
            RetrofitInstance.api.getCurrentWeather(
                "tokyo",
                "metric",
                "41ac77b7f759a36017e8b177d86c001c"
            )
        } catch (e: IOException) {
            //Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_SHORT) .show()
            return@launch
        } catch (e: HttpException) {
            //Toast.makeText(applicationContext, "http error ${e.message}", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (response.isSuccessful && response.body() != null) {
            withContext(Dispatchers.Main) {
                binding.tvTemp.text = "${response.body()!!.weather}"
            }
        }
    }
*/