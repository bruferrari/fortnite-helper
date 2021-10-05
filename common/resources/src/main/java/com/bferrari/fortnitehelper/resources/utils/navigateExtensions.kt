package com.bferrari.fortnitehelper.resources.utils

import androidx.navigation.NavController

fun NavController.navigate(route: String, newTask: Boolean = false, isInclusive: Boolean = true) {
    navigate(route) {
        if (newTask) {
            currentDestination?.id?.let { destinationId ->
                popUpTo(destinationId) { inclusive = isInclusive }
            }
        }
    }
}