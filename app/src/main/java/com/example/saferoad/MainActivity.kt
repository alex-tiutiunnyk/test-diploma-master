package com.example.saferoad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.saferoad.components.MyTopAppBar
import com.example.saferoad.navigation.AppRouter
import com.example.saferoad.navigation.Screen
import com.example.saferoad.screens.LoginScreen
import com.example.saferoad.screens.SignUpScreen
import com.example.saferoad.ui.theme.SafeRoadTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafeRoadTheme {
                SafeRoadApp()
            }
        }
    }

    @Composable
    fun SafeRoadApp() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Scaffold(
                topBar = {
                    MyTopAppBar()
                }) {
                Crossfade(targetState = AppRouter.currentScreen) { currentState ->
                    when (currentState.value) {
                        is Screen.SignUpScreen -> {
                            SignUpScreen()
                        }

                        is Screen.LoginScreen -> {
                            LoginScreen()
                        }
                    }
                }
            }
        }
    }


    ////


}

//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    val currentLocation = LatLng(location.latitude, location.longitude)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Initialize location services
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        // Set up content view
//        setContent {
//            MyMapApp(fusedLocationClient.)
//        }
//
//        // Check location permissions
//        requestLocationPermission {
//            if (it) {
//                fetchGPSCoordinates()
//            } else {
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    // Function to request location permission
//    private fun requestLocationPermission(onResult: (Boolean) -> Unit) {
//        val locationPermissionRequest = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted ->
//            onResult(isGranted)
//        }
//
//        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//    }
//
//    // Fetch GPS coordinates
//    private fun fetchGPSCoordinates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//                location?.let {
//                    val latLng = LatLng(it.latitude, it.longitude)
//                    sendLocationToKafka(latLng)
//                }
//            }
//        }
//    }
//
//    // Send GPS coordinates to Kafka
//    private fun sendLocationToKafka(location: LatLng) {
//        val producer = createKafkaProducer()
//
//        val record = ProducerRecord<String, String>(
//            "gps-topic",  // Use your Heroku Kafka topic name here
//            null,  // Key (optional)
//            "${location.latitude},${location.longitude}"  // Value
//        )
//
//        CoroutineScope(Dispatchers.IO).launch {
//            producer.send(record)
//            producer.flush()
//            producer.close()
//        }
//    }
//
//    // Create Kafka producer
//    private fun createKafkaProducer(): KafkaProducer<String, String> {
//        val props = Properties()
//        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "your-kafka-broker-url:9092"
//        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
//        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
//        return KafkaProducer(props)
//    }

@Composable
fun MyMapApp(latLng: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 10f)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = latLng),
                title = "User's current location"
            )
        }
    }
}

private fun createKafkaProducer(): KafkaProducer<String, String> {
    val props = Properties()
    // Use your Heroku Kafka broker URL
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "your-heroku-kafka-broker-url:9092"

    // Kafka producer settings
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"

    return KafkaProducer(props)
}
