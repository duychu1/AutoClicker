package com.duycomp.autoclicker.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val intervalClick: Long = 100L,
    val durationClick: Long = 10L,
    val nLoop: Int = 1,
    val isInfinityLoop: Boolean = false,
    val earlyTime: Long = 300L
)
