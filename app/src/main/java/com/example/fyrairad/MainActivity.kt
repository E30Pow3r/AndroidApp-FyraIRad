package com.example.fyrairad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fyrairad.ui.theme.FyraIRadTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

var currPlayer: Int = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FyraIRadTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "spelplan") {
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FilledTonalButton(onClick = { navController.navigate("spelplan") })
            {
                Text("Spela 4 I Rad")
            }

    }
}

@Composable
fun PlayScreen(navController: NavController) {
    val gridItems = remember { mutableStateListOf<Int>().apply { repeat(42) { add(R.drawable.empty) } } }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 400.dp, bottom = 100.dp)
                //.background(color = Color. Green) // Solid element background color
            ,contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .padding(0.dp),
                horizontalArrangement = Arrangement.spacedBy(-4.dp), // Adjust this value to change horizontal spacing
                verticalArrangement = Arrangement.spacedBy(-0.2.dp) // Adjust this value to change vertical spacing

            ) {
                items(gridItems.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(52.8.dp)
                            .offset(x = -0.5.dp, y = 7.dp)
                        ,contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = gridItems[index]),
                            contentDescription = "Grid Item"
                            ,Modifier.size(45.dp)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spelfalt),
                    contentDescription = "FyraIRad Image",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until 7) {
                    TextButton(
                        onClick = {
                            for (j in 5 downTo 0) {
                                if (gridItems[j * 7 + i] == R.drawable.empty) {
                                    gridItems[j * 7 + i] = 
                                    break
                                }
                            }
                        },
                        Modifier
                            .height(350.dp)
                            .width(54.dp)
                    ) {
                        Text("${i + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun SlideImage(X: Int, Y: Int) {
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