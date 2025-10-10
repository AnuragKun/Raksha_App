package com.arlabs.raksha.Presentation.MainScreens.SafeZone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SafeZoneScreen(modifier: Modifier = Modifier){
    Column (modifier = modifier.fillMaxSize()
        .background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "Safe Zone Screen",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.White
        )
    }
}