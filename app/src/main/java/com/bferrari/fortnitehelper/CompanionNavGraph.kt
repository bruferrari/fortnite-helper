package com.bferrari.fortnitehelper

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bferrari.features.news.ui.News
import com.bferrari.features.shop.ui.ShopScreen
import com.bferrari.fortnitehelper.ui.SplashScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object MainDestinations {
    const val SplashRoute = "splash"
    const val ShopRoute = "shop"
    const val NewsRoute = "news"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CompanionNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.SplashRoute
) {
    val scope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.SplashRoute) {
            SplashScreen(navController)
        }

        composable(MainDestinations.ShopRoute) {
            ShopScreen(
                viewModel = getViewModel {
                    parametersOf(scope)
                }
            )
        }

        composable(MainDestinations.NewsRoute) {
            News(
                viewModel = getViewModel {
                    parametersOf(scope)
                }
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}