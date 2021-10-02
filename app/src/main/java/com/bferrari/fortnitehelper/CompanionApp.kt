package com.bferrari.fortnitehelper

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bferrari.fortnitehelper.resources.theme.ZeroPointDesignSystem
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CompanionApp() {
    ZeroPointDesignSystem {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        Scaffold {
            CompanionNavGraph(navController)
        }
    }
}