package com.arlabs.raksha.Presentation.MainScreens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.R

@Composable
fun HomeScreen(){
    var userName by remember { mutableStateOf("Temporary") }
    var CurrentLocation by remember { mutableStateOf("") }
    var DestinationLocation by remember { mutableStateOf("") }

    Scaffold { innerPadding->
        GradientBox(modifier = Modifier
            .fillMaxSize()) {
            Column (modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
                .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start){
                Text(
                    "Hello!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp),
                    color = Color.White
                )
                Text(
                    text = userName,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 12.dp),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column (modifier = Modifier
                .align(Alignment.Center)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("Find Your Path",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray.copy(alpha = 0.5f)
                )

                OutlinedTextField(
                    value = CurrentLocation,
                    onValueChange = {CurrentLocation = it},
                    label = {Text("Enter your Current Location")},
                    leadingIcon = { Icon(imageVector = Icons.Default.AddLocation, // capital D in Default
                            contentDescription = "") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 21.dp),
                    colors = TextFieldDefaults.colors( // Use TextFieldDefaults.colors
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    )
                )

                OutlinedTextField(
                    value = DestinationLocation,
                    onValueChange = {DestinationLocation = it},
                    label = {Text("Enter your Destination")},leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddLocation, // capital D in Default
                            contentDescription = ""
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 21.dp),
                    colors = TextFieldDefaults.colors( // Use TextFieldDefaults.colors
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {},
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 90.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.5f), contentColor = Color.White)
                    ) {
                    Text("Start")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(modifier = Modifier
                    .background(Color.White.copy(alpha = 0.0f))
                    .fillMaxWidth()
                    .padding(21.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.3f))
                    .padding(21.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text("Emergency Contacts",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))


                    Row {
                        OutlinedButton (onClick = {}) {
                            Text("Aayush")
                        }

                        Spacer(modifier = Modifier.width(39.dp))

                        Button (onClick ={ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC0CB),contentColor=Color.White)) {
                            Text("Call")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        OutlinedButton (onClick = {}) {
                            Text("Aayush")
                        }

                        Spacer(modifier = Modifier.width(39.dp))

                        Button (onClick ={ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC0CB),contentColor=Color.White)) {
                            Text("Call")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        OutlinedButton (onClick = {}) {
                            Text("Aayush")
                        }

                        Spacer(modifier = Modifier.width(39.dp))

                        Button (onClick ={ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC0CB),contentColor=Color.White)) {
                            Text("Call")
                        }
                    }

                }

                Spacer(modifier = Modifier.height(49.dp))

                Button(onClick = {},
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 39.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.5f), contentColor = Color.White))
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sharelocation),
                        contentDescription = null,
                        modifier = Modifier.size(37.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Share Location", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview(){
    HomeScreen()
}