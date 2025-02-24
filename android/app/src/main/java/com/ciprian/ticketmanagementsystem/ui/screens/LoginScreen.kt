package com.ciprian.ticketmanagementsystem.ui.screens

import Logo
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciprian.ticketmanagementsystem.R
import com.ciprian.ticketmanagementsystem.ui.theme.BabyBlue
import com.ciprian.ticketmanagementsystem.ui.theme.BlueGrotto
import com.ciprian.ticketmanagementsystem.ui.theme.RoyalBlue
import com.ciprian.ticketmanagementsystem.ui.theme.TicketManagementSystemTheme
import com.ciprian.ticketmanagementsystem.ui.theme.White
import com.ciprian.ticketmanagementsystem.ui.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginResult by loginViewModel.loginResult.collectAsState()

    LaunchedEffect(loginResult) {
        loginResult?.let {
            if (it.isSuccess) {
                onLoginSuccess()
            } else {
                Toast.makeText(context, it.exceptionOrNull()?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(id = R.string.username)) },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(Color.White, shape = RoundedCornerShape(16.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(Color.White, shape = RoundedCornerShape(16.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { loginViewModel.login(username, password) },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }
    }
}
