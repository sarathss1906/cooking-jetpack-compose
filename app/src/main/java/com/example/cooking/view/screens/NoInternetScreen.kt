package com.example.cooking.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cooking.R
import com.example.cooking.ui.theme.Black


@Composable
fun NoInternet(){
    
    Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.no_internet_icon),
            contentDescription = stringResource(R.string.no_internet_icon)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = stringResource(id = R.string.no_network_toast),
            color = Black,
        fontSize = 18.sp)
    }
    
}