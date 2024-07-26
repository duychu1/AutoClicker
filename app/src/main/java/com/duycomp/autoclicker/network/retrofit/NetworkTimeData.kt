package com.duycomp.autoclicker.network.retrofit

data class NetworkTimeData(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val seconds:Int,
    val milliSeconds: Int,
    val dateTime: String,
    val date: String,
    val time: String,
    val timeZone: String,
    val dayOfWeek: String,
    val dstActive: Boolean
)
