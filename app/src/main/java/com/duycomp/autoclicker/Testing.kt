package com.duycomp.autoclicker

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun main() {
    val map: MutableStateFlow<MutableMap<String, Int>> = MutableStateFlow(mutableMapOf("a" to 1, "b" to 2))
    println(map.value.size)

    CoroutineScope(Dispatchers.Default).launch {
        println("thread: ${Thread.currentThread().name}")
        delay(1000)
        withContext(Dispatchers.Main) {
            println("thread: ${Thread.currentThread().name}")

            map.value.remove("key1")
            println("remove: ${map.value.size}")
        }
    }

    CoroutineScope(Dispatchers.Default).launch {
        println("thread: ${Thread.currentThread().name}")

        delay(2000)
        withContext(Dispatchers.Main) {
            println("thread: ${Thread.currentThread().name}")
            map.value["key3"] = 3
            println("add: ${map.value.size}")
        }
    }
}






