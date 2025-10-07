package com.arlabs.raksha.Presentation.onBoarding.Components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * Composable for the navigation buttons at the bottom.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingNavButtons(
    pagerState: PagerState,
    onGetStartedClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.size - 1

    Button(
        onClick = {
            if (isLastPage) {
                onGetStartedClicked()
            } else {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = tween(500))
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .padding(bottom = 24.dp), // Added more padding at the bottom
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.3f),
            contentColor = Color.White
        )
    ) {
        Text(text = if (isLastPage) "Get Started" else "Next", fontSize = 16.sp)
    }
}