package com.duycomp.autoclicker.feature.accessibility

import com.duycomp.autoclicker.model.TimerSchedule

class ActionTest {
    // Define a sample TimerSchedule object for testing
    private val timerSchedule = TimerSchedule(timeMs = 1670000000L, earlyClick = 10000L)
    private val dayInMillis = 86400000L // 24 hours in milliseconds

//    @Test
//    fun `calculateDelay returns positive delay when time is in the future`() {
//        val systemDayMillis = 1669000000L
//        val clockOffset = 0
//        val expectedDelay = timerSchedule.timeMs - systemDayMillis + clockOffset - timerSchedule.earlyClick
//        val actualDelay = calculateDelay(0L, timerSchedule, systemDayMillis, clockOffset)
//        assertEquals(expectedDelay, actualDelay)
//    }
//
//    @Test
//    fun `calculateDelay returns positive delay when time is in the past but within a day`() {
//        val systemDayMillis = 1671000000L
//        val clockOffset = 0
//        val expectedDelay = timerSchedule.timeMs - systemDayMillis + clockOffset - timerSchedule.earlyClick + dayInMillis
//        val actualDelay = calculateDelay(0L, timerSchedule, systemDayMillis, clockOffset)
//        assertEquals(expectedDelay, actualDelay)
//    }
//
//    @Test
//    fun `calculateDelay accounts for clock offset`() {
//        val systemDayMillis = 1669500000L
//        val clockOffset = 5000L
//        val expectedDelay = timerSchedule.timeMs - systemDayMillis + clockOffset - timerSchedule.earlyClick
//        val actualDelay = calculateDelay(0L, timerSchedule, systemDayMillis, clockOffset.toInt())
//        assertEquals(expectedDelay, actualDelay)
//    }
}