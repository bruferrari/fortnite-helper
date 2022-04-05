package com.bferrari.fortnitehelper.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bferrari.fortnitehelper.MainDestinations
import com.bferrari.fortnitehelper.R
import com.bferrari.fortnitehelper.resources.utils.navigate
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun SplashScreen(navController: NavHostController) {
    val animState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    )

    AnimatedVisibility(
        visibleState = animState,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = fadeOut() + shrinkOut()
    ) {
        Column {
            SplashMessage(
                message = Message(
                    stringResource(id = R.string.app_title),
                    stringResource(id = R.string.app_subtitle)
                )
            )
        }
    }

    LaunchedEffect(true) {
        delay(1000)
//        navController.navigate(MainDestinations.ShopRoute, true)
        navController.navigate(MainDestinations.NewsRoute, true)
    }
}

@Composable
fun SplashMessage(message: Message) {
    val fortniteFont = FontFamily(
        Font(R.font.fortnite, FontWeight.Normal)
    )

    val textStyle: TextStyle = LocalTextStyle.current.copy(
        fontFamily = fortniteFont,
        color = colorResource(R.color.gray),
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