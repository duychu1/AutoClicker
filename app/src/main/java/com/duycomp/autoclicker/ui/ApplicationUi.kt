package com.duycomp.autoclicker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.duycomp.autoclicker.feature.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationUi() {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("AutoClicker") }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.surface
        ) {

            HomeScreen()
        }
    }
}