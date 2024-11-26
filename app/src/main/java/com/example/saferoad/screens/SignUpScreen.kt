package com.example.saferoad.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saferoad.components.*
import com.example.saferoad.navigation.AppRouter
import com.example.saferoad.navigation.Screen

@Composable
fun SignUpScreen() {
    val loginValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
            .padding(top = 90.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = "Hey there,")
            HeadingTextComponent(value = "Create an Account")
            Spacer(modifier = Modifier.height(40.dp))

            MyTextFieldComponent(
                labelValue = "Login",
                icon = Icons.Outlined.Person,
                value = loginValue.value,
                onValueChange = { loginValue.value = it }
            )
            MyTextFieldComponent(
                labelValue = "Email",
                icon = Icons.Outlined.Email,
                value = emailValue.value,
                onValueChange = { emailValue.value = it })
            
            PasswordTextFieldComponent(
                labelValue = "Password",
                icon = Icons.Outlined.Lock,
                value = passwordValue.value,
                onValueChange = { passwordValue.value = it })
            Spacer(modifier = Modifier.height(50.dp))

            ButtonComponent(value = "Register", onButtonClicked = {/*TODO*/ })
            Spacer(modifier = Modifier.height(20.dp))

            ClickableLoginTextComponent(isLoginPage = false, onTextSelected = {
                AppRouter.navigateTo(Screen.LoginScreen)
            })
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}