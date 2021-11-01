package com.bferrari.fortnitehelper

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bferrari.features.shop.ui.ShopScreen
import com.bferrari.fortnitehelper.ui.SplashScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

object MainDestinations {
    const val SPLASH_ROUTE = "splash"
    const val SHOP_ROUTE = "shop"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CompanionNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.SPLASH_ROUTE
) {
    val scope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.SPLASH_ROUTE) {
            SplashScreen(navController)
        }

        composable(MainDestinations.SHOP_ROUTE) {
            ShopScreen(
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