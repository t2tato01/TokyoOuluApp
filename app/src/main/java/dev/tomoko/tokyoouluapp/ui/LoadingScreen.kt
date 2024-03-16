package dev.tomoko.tokyoouluapp.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun LoadingScreen() {
    /*Text(
        text = "Loading...",
        modifier = Modifier.fillMaxSize(),
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )*/
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White.copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer(
                        rotationY = 360f
                    ),
                color = Color.Black
            )
           /* Text(
                text = "Loading...",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )*/
        }
    }
}