package com.example.fyrairad

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fyrairad.ui.theme.FyraIRadTheme
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FyraIRadTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "lobby") {
                    composable("spelplan") { PlayScreen(navController) }        // Säger: Dom här ska finnas i min router
                    composable("lobby") { Lobby(navController) }
                }


            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Lobby(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(50.dp)
    ){
        Box(Modifier
            .align(Alignment.CenterHorizontally)
        ) {
            TextButton(onClick = { navController.navigate("spelplan") })
            {
                Text("Spela 4 I Rad")
            }
        }
    }
}

@Composable
fun PlayScreen(navController: NavController) {
    val showSlideImage = remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.spelfalt),
                contentDescription = "FyraIRad Image",
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..7) {       // Loopar programet så att man får 7 knappar
                TextButton(
                    onClick = { showSlideImage.value = true },
                    Modifier
                        .height(350.dp)
                        .width(54.dp)
                        .padding(0.dp)
                ) {
                    Text("$i")
                }
            }
            if (showSlideImage.value) {
                SlideImage()
            }
        }
    }
}

@Composable
fun SlideImage() {
    val offsetY = remember { Animatable(450f) }

    LaunchedEffect(Unit) {
        launch {
            offsetY.animateTo(
                targetValue = 965f,
                animationSpec = tween(durationMillis = 2000)
            )
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.red_player),
            contentDescription = "Sliding Image",
            modifier = Modifier
                .size(45.dp)
                .offset { IntOffset(13, offsetY.value.roundToInt()) }
        )
    }
}