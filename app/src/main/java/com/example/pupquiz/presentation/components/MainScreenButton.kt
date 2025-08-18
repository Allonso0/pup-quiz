package com.example.pupquiz.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun MainScreenButton(
    onButtonClick: () -> Unit,
    buttonText: String
) {
    val customColors = LocalCustomColors.current

    Button(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = customColors.mainMenuButtonColor,
            contentColor = customColors.thirdTextColor
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = onButtonClick
    ) {
        Text (
            text = buttonText,
            fontFamily =  MyCustomFont,
            fontSize = 42.sp
        )
    }
}