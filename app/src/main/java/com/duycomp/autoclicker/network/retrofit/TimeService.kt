package com.duycomp.autoclicker.network.retrofit

import retrofit2.http.GET

interface TimeService {
    @GET("Time/current/zone?timeZone=UTC")
    suspend fun getTimeData(): NetworkTimeData
}