package com.example.saferoad.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saferoad.components.*
import com.example.saferoad.navigation.AppRouter
import com.example.saferoad.navigation.Screen
import com.example.saferoad.navigation.SystemBackButtonHandler
import com.example.saferoad.utils.kafka.KafkaProducer

@Composable
fun LoginScreen() {

    val scope = rememberCoroutineScope()
    val loginValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
            .padding(top = 90.dp)

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = "Hey there,")
            HeadingTextComponent(value = "Welcome Back")
            Spacer(modifier = Modifier.height(40.dp))

            MyTextFieldComponent(
                labelValue = "Login",
                icon = Icons.Outlined.Person,
                value = loginValue.value,
                onValueChange = { loginValue.value = it })

            PasswordTextFieldComponent(
                labelValue = "Password",
                icon = Icons.Outlined.Lock,
                value = passwordValue.value,
                onValueChange = { passwordValue.value = it })

            Spacer(modifier = Modifier.height(50.dp))

            ButtonComponent(value = "Login", onButtonClicked = {
                val kafkaProducer = KafkaProducer()
                kafkaProducer.sendMessage("login_topic", loginValue.value, passwordValue.value)
//                isLoading = true
//                scope.launch(Dispatchers.IO) {
//                    val isSuccess = performLogin(loginValue.value, passwordValue.value)
////                    isLoading = false
//                    if (isSuccess) {
//                        print("[Login:: $loginValue.value] Login verification successful!")
//                        AppRouter.navigateTo(Screen.SignUpScreen) //TODO: change this
//                    } else {
//                        print("[Login:: $loginValue.value] Login verification failed.")
//                        errorMessage = "Invalid login or password"
//                    }
//                }
            })
            Spacer(modifier = Modifier.height(20.dp))

            // Error message display
            if (errorMessage.isNotEmpty()) {
                NormalTextComponent(value = errorMessage, color = Color.Red)
            }

            ClickableLoginTextComponent(isLoginPage = true, onTextSelected = {
                AppRouter.navigateTo(Screen.SignUpScreen)
            })
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.SignUpScreen)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}