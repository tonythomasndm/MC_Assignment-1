package com.example.mc_assignment_part1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Stop(
    val name: String,
    val distance: Double,
    var visited: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lazyStops = remember {
                mutableListOf(
                    Stop("Kashmere Gate", 0.0),
                    Stop("Lal Killa", 26.0),
                    Stop("ITO", 18.0),
                    Stop("Mandi House", 42.0),
                    Stop("Central Secratriat", 29.0),
                    Stop("Lajpat Nagar", 24.0),
                    Stop("Moolchand", 14.0),
                    Stop("Kailash Colony", 12.0),
                    Stop("Nehru Place", 35.0),
                    Stop("Kailash Nagar", 27.0),
                    Stop("Govind Puri", 36.0),
                    Stop("Harkesh Nagar Okhla", 11.0)
                )
            }
            val normalStops = remember {
                mutableListOf(
                    Stop("Kailash Nagar", 0.0),
                    Stop("Govind Puri", 36.0),
                    Stop("Harkesh Nagar Okhla", 11.0)
                )
            }
            val selectedStopList = remember { mutableStateOf(lazyStops) }
            Column() {
                AppName()
                Button(
                    onClick = {
                        // Toggle between lazy and normal stops
                        selectedStopList.value = if (selectedStopList.value == lazyStops) {
                            normalStops
                        } else {
                            lazyStops
                        }
                    },
                    modifier=Modifier.padding(12.dp)
                ) {
                    Text(if (selectedStopList.value == lazyStops) "Show Normal Stops" else "Show Lazy Stops")
                }

                App(selectedStopList.value)

            }
        }
    }
}


@Composable
fun App(stops: List<Stop>) {
    val currentStopIndex = remember { mutableStateOf(0) }
    val inKm = remember { mutableStateOf(true) }
    val totalDistance = stops.sumOf { it.distance }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Button(
            onClick = {
                inKm.value = !inKm.value
            }
        ) {
            Text(if (inKm.value) "Show in Miles" else "Show in Kilometres")
        }

        Spacer(modifier = Modifier.height(12.dp))

        val name = stops[currentStopIndex.value].name
        Text("Current Stop: $name")

        val distanceToNextStop = if (currentStopIndex.value < stops.size - 1) {
            stops[currentStopIndex.value + 1].distance
        } else {
            0.0
        }
        if (distanceToNextStop != 0.0) {
            Text("Next stop is at distance : ${distanceText(distanceToNextStop, inKm.value)}")
        } else {
            Text("Next stop is at distance : No next stop available ")
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (currentStopIndex.value < stops.size - 1) {
                    currentStopIndex.value++
                }
            },
            enabled = currentStopIndex.value < stops.size - 1
        ) {
            Text("Next Stop")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Total Distance Covered: ${distanceText(totalDistance, inKm.value)}")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Total Distance Left: ${
            distanceText(
                totalDistance - stops.take(currentStopIndex.value + 1)
                    .sumOf { it.distance },
                inKm.value
            )
        }")

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = if (totalDistance != 0.0) {
                (stops.take(currentStopIndex.value + 1).sumOf { it.distance } / totalDistance).toFloat()
            } else {
                0f
            },
            modifier = Modifier.fillMaxWidth()
        )
        val progressPercentage = if (totalDistance != 0.0) {
            ((stops.take(currentStopIndex.value + 1).sumByDouble { it.distance } / totalDistance) * 100).toInt()
        } else {
            0
        }
        Text(
            text = "Progress: $progressPercentage%",
            style = TextStyle(fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Stops List:")
        if (stops.size >= 10) {
            LazyColumn {
                items(stops) { stop ->
                    StopStation(
                        stop = stop,
                        inKm = inKm.value,
                        isCurrent = stop == stops[currentStopIndex.value]
                    )
                }
            }
        } else {
            Column {
                stops.forEach { stop ->
                    StopStation(
                        stop = stop,
                        inKm = inKm.value,
                        isCurrent = stop == stops[currentStopIndex.value]
                    )
                }
            }
        }
    }
}



@Composable
fun StopStation(stop: Stop, inKm: Boolean, isCurrent: Boolean) {
    var backgroundColor = when {
        isCurrent -> Color(0xFF006400)
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor)
    ) {
        Text(
            text = "${stop.name}: ${distanceText(distance = stop.distance, inKm = inKm)}",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}



@Composable
fun distanceText(distance: Double, inKm: Boolean): String {
    return if (inKm) {
        String.format("%.2f km", distance)
    } else {
        val milesDistance = distance * 0.62137
        String.format("%.2f miles", milesDistance)
    }
}



@Composable
fun AppName(){
    Box(modifier= Modifier
        .fillMaxWidth()
        .padding(15.dp),
        contentAlignment = Alignment.Center){
        Column {
            Text(text="StopCalculator", modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Divider(color = Color.Gray, thickness = 4.dp)
        }
    }
}
