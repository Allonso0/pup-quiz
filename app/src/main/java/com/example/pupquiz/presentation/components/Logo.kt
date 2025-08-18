package com.example.pupquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun Logo() {
    val customColors = LocalCustomColors.current

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PUP",
            color = customColors.logoColor,
            fontFamily = MyCustomFont,
            fontSize = 128.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "QUIZ",
            color = customColors.logoColor,
            fontFamily = MyCustomFont,
            fontSize = 96.sp,
            textAlign = TextAlign.Center
        )
    }
}