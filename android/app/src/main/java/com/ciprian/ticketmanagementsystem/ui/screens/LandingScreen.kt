package com.ciprian.ticketmanagementsystem.ui.screens

import Logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ciprian.ticketmanagementsystem.R

@Composable
fun LandingScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onExploreClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_bg),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            Logo()
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onExploreClick,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = stringResource(id = R.string.explore))
                }
            }
        }
    }
}
