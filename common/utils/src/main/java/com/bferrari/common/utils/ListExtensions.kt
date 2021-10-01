package com.bferrari.common.utils

inline fun <reified T> List<T>.concat(
    vararg lists: List<T>
): List<T> = listOf(this, *lists).flatten()