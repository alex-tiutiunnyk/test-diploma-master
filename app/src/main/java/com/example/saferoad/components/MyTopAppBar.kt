package com.example.saferoad.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmergencyShare
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.saferoad.ui.theme.SafeRoadTheme
import com.example.saferoad.ui.theme.VioletMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    val context = LocalContext.current.applicationContext
    TopAppBar(
        title = {
            Text(
                text = "SafeRoad",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Outlined.EmergencyShare,
                    contentDescription = ""
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = VioletMain,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    SafeRoadTheme {
        MyTopAppBar()
    }
}
