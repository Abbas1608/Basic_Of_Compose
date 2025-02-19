package com.example.constraintlayoutapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun list()
{
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ) {

    }
}
