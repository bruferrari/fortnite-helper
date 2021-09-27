package com.bferrari.fortnitehelper

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SplashScreen() }
    }

    @Preview
    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.bg))
        )
        SplashMessage(
            message = Message("Fortnite", "Helper")
        )
    }

    @Composable
    fun SplashMessage(message: Message) {
        val fortniteFont = FontFamily(
            Font(R.font.fortnite, FontWeight.Normal)
        )

        val textStyle: TextStyle = LocalTextStyle.current.copy(
            fontFamily = fortniteFont,
            color = colorResource(R.color.grey),
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message.title,
                style = textStyle,
                fontSize = 56.sp
            )
            Text(
                text = message.subtitle,
                style = textStyle,
                fontSize = 32.sp
            )
        }
    }

    data class Message(
        val title: String,
        val subtitle: String
    )
}