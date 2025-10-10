package com.arlabs.raksha.Presentation.MainScreens.Profile

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
fun ProfileScreen(modifier: Modifier = Modifier){
    Column (modifier = modifier.fillMaxSize()
        .background(Color.Gray),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "Profile Screen",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.Blue
        )
    }
}