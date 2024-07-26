package com.duycomp.autoclicker.network

import android.util.Log
import com.duycomp.autoclicker.common.result.Result
import com.duycomp.autoclicker.network.model.AcResponse
import com.duycomp.autoclicker.network.model.fromJsonToTimeIO
import com.duycomp.autoclicker.network.model.fromJsonToWorldClock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


suspend fun getClockOffsetTimeIO(currentClockOffset: Long = 0L): Result<Long> =
    withContext(Dispatchers.IO) {
        val url = "https://www.timeapi.io/api/Time/current/zone?timeZone=UTC"

        var clockOffsetCurrent = currentClockOffset
        repeat(10) { attempt ->
            val result = try {
                acRequest(url)
            } catch (e: Exception) {
                Result.Error(e) // Handle specific exceptions
            }

            when (result) {
                is Result.Error -> {
                    // Log error with attempt number
                    Log.e(TAG, "Error fetching time (attempt $attempt): ${result.exception?.message}")
                    delay(500) // Delay before retry
                }
                is Result.Success -> {
                    val acResponse = result.data
                    val timeIO = fromJsonToTimeIO(acResponse.strJson)
                        ?: return@withContext Result.Error(Exception("Parsing failed"))

                    val timeOnlineMilis =
                        timeIO.minute * 60000 + timeIO.seconds * 1000 + timeIO.milliSeconds

                    val systemMilis = acResponse.timeReceive % 3600000

                    val newClockOffset = systemMilis - timeOnlineMilis

                    Log.d(TAG, "getClockOffsetTimeIO: $newClockOffset")

                    if (clockOffsetCurrent - newClockOffset < 50 && clockOffsetCurrent - newClockOffset > -50) {
                        return@withContext Result.Success(newClockOffset)
                    } else {
                        clockOffsetCurrent = newClockOffset
                    }
                }

                else -> {}
            }

        }

        return@withContext Result.Success(clockOffsetCurrent)

    }

suspend fun getClockOffsetWorldClock(currentClockOffset: Long = 0L): Result<Long> =
    withContext(Dispatchers.IO) {
        val url = "http://worldclockapi.com/api/json/utc/now"

        var clockOffsetCurrent = currentClockOffset
        for (i in 1..10) {

            var result = acRequest(url)

            for (j in 1..4) {
                if (result is Result.Success) break
                else {
                    delay(500)
                    result = acRequest(url)
                }
            }

            when (result) {
                is Result.Error -> {
                    return@withContext Result.Error(result.exception)
                }

                is Result.Success -> {
                    val acResponse = result.data
                    val worldClock = fromJsonToWorldClock(acResponse.strJson)
                        ?: return@withContext Result.Error()

                    val timeOnlineMilis = (worldClock.currentFileTime.div(10000)) % 100000
                    val systemMilis = acResponse.timeReceive % 100000

                    val newClockOffset = systemMilis - timeOnlineMilis

                    Log.d(TAG, "getClockOffsetWorldClock: $newClockOffset")

                    if (clockOffsetCurrent - newClockOffset < 50 && clockOffsetCurrent - newClockOffset > -50) {
                        return@withContext Result.Success(newClockOffset)
                    } else {
                        clockOffsetCurrent = newClockOffset
                    }
                }

                else -> {}
            }

        }

        return@withContext Result.Success(clockOffsetCurrent)

    }

private suspend fun acRequest(url: String): Result<AcResponse> {
    var connection: HttpURLConnection? = null
    try {
        val u = URL(url)
        connection = u.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json; utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.connect()

        when (connection.responseCode) {
            200, 201 -> {
                val systemTime = System.currentTimeMillis()
                val br = BufferedReader(InputStreamReader(connection.inputStream, "utf-8"))
                val sb = StringBuilder()
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                br.close()
                return Result.Success(AcResponse(systemTime, sb.toString()))
            }
        }
    } catch (ex: MalformedURLException) {
        ex.printStackTrace()
        Result.Error(ex)
    } catch (ex: IOException) {
        ex.printStackTrace()
        Result.Error(ex)
    } finally {
        if (connection != null) {
            try {
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return Result.Error()
}

private const val TAG = "ClockOffset"