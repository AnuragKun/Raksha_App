package com.arlabs.raksha.Presentation.onBoarding.Components

import androidx.annotation.DrawableRes
import com.arlabs.raksha.R

data class Page (
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

val pages = listOf(
    Page(imageRes = R.drawable.ic_raksha_logo,
        title = "Navigate Smarter",
        description = "Don't just find the fastest route, find the safest one. We guide you through paths vetted for safety by our community."
    ),
    Page(imageRes = R.drawable.ic_raksha_logo,
        title = "Power in Unity",
        description = "Join a network of verified users making travel safer for everyone. Report unsafe spots and help build a trusted map."
    ),
    Page(imageRes = R.drawable.ic_raksha_logo,
        title = "Automatic Guardian",
        description = "Set a safety timer for your journey. If you don't check in on time, we automatically alert your emergency contacts for you."
    )
)