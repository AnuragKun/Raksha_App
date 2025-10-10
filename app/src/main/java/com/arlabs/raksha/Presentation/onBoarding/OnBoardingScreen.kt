package com.arlabs.raksha.Presentation.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.arlabs.raksha.navigation.Routes
import com.arlabs.raksha.Presentation.onBoarding.Components.OnboardingPageItem
import com.arlabs.raksha.Presentation.onBoarding.Components.pages

//@Composable
//fun OnBoardingScreen(){
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(R.drawable.ic_raksha_logo),
//                contentDescription = "App logo",
//                modifier = Modifier.size(250.dp),
//
//                )
//        }
//        GradientBox(
//            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f).align(Alignment.BottomCenter).clip(
//                TopCurveShape())
//        ) {
//            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = Page.description,
//                    color = Color.White,
//                    fontSize = 40.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "Feature description",
//                    color = Color.White.copy(alpha = 0.8f),
//                    fontSize = 18.sp
//                )
//                Spacer(modifier = Modifier.height(32.dp))
//                val onGetStartedClicked = null
//                Button(
//                    onClick = { onGetStartedClicked },
//                    shape = RoundedCornerShape(16.dp),
//                    modifier = Modifier
//                        .fillMaxWidth(0.7f)
//                        .height(50.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White.copy(alpha = 0.3f),
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(text = "Get Started", fontSize = 16.sp)
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(onGetStartedClicked: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { pageIndex ->
        // Pass the pagerState and the final click action to each page item
        OnboardingPageItem(
            page = pages[pageIndex],
            pagerState = pagerState,
            onGetStartedClicked = onGetStartedClicked
        )
    }
}




