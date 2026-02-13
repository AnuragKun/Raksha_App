package com.arlabs.raksha.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.arlabs.raksha.features.home.HomeScreen
import com.arlabs.raksha.features.profile.ProfileScreen
import com.arlabs.raksha.features.report.ReportIncidentScreen
import com.arlabs.raksha.features.safezone.SafeZoneScreen
import com.arlabs.raksha.R
import com.arlabs.raksha.navigation.NavItem
import com.google.firestore.admin.v1.Index

import androidx.navigation.NavController

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController){

    val navItemList = listOf(
        NavItem("Home", painterResource(id = R.drawable.ic_home)),
        NavItem("Safe Zone", painterResource(id = R.drawable.ic_safe_zone)),
        NavItem("Report", painterResource(id = R.drawable.ic_report)),
        NavItem("Profile", painterResource(id = R.drawable.ic_profile))
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = Color.White
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(painter = navItem.icon, contentDescription = "Icon") },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex, navController)

    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, navController: NavController){
    when(selectedIndex){
        0 -> HomeScreen(navController = navController)
        1 -> SafeZoneScreen()
        2 -> ReportIncidentScreen()
        3 -> ProfileScreen(navController = navController)

    }
}