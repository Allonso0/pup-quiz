package com.example.pupquiz.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pupquiz.R
import com.example.pupquiz.presentation.ui.theme.LocalCustomColors
import com.example.pupquiz.presentation.ui.theme.MyCustomFont

@Composable
fun BreedCard(
    breedName: String,
    onItemClick: () -> Unit
) {
    val customColors = LocalCustomColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onItemClick),
        colors = CardDefaults.cardColors(
            containerColor = customColors.secondBackground
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = breedName.replaceFirstChar { it.uppercaseChar() },
                fontFamily = MyCustomFont,
                fontSize = 20.sp,
                color = customColors.textColor
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_click),
                tint = customColors.iconColor,
                contentDescription = "click",
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}