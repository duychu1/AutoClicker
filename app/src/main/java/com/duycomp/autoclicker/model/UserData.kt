package com.duycomp.autoclicker.model

data class UserData(
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.DARK,
    val useDynamicColor: Boolean = true,
    val intervalClick: Long = 100L,
    val durationClick: Long = 10L,
    val nLoop: Int = 1,
    val isInfinityLoop: Boolean = false,
    val earlyTime: Long = 300L
)
