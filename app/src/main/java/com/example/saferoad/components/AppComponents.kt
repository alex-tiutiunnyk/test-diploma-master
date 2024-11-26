package com.example.saferoad.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saferoad.ui.theme.BgColor
import com.example.saferoad.ui.theme.Purple80
import com.example.saferoad.ui.theme.VioletMain
import com.example.saferoad.ui.theme.componentShapes

@Composable
fun NormalTextComponent(value: String, color: Color = Color.Black) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
        color = color,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldComponent(labelValue: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().clip(
            componentShapes.small
        ),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
        leadingIcon =
        {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        }
    )
}

@Composable
fun PasswordTextFieldComponent(labelValue: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {

    val localFocusManager = LocalFocusManager.current
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().clip(
            componentShapes.small
        ),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        value = value,
        onValueChange = onValueChange,
        leadingIcon =
        {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                "Hide password"
            } else {
                "Show password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        onClick = { onButtonClicked.invoke() },
        modifier = Modifier.fillMaxWidth().heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().heightIn(48.dp).background(
                brush = Brush.horizontalGradient(listOf(VioletMain, Purple80)),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ClickableLoginTextComponent(isLoginPage: Boolean = false, onTextSelected: (String) -> Unit) {
    val initialText = if (isLoginPage) "Don't have an account yet?  " else "Already have an account?  "
    val loginText = if (isLoginPage) "Register" else "Login"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = VioletMain)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableLoginTextComponent", "{${span.item}}")
                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }
        })
}