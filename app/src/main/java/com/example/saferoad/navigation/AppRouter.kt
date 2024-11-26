package com.example.saferoad.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object SignUpScreen : Screen()
    object LoginScreen : Screen()
}

object AppRouter {
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}