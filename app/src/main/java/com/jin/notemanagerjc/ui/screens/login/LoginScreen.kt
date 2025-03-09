package com.jin.notemanagerjc.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jin.notemanagerjc.MainViewModel
import com.jin.notemanagerjc.Screen
import com.jin.notemanagerjc.common.enumz.LoadStatus

@Composable
fun LoginScreen(
    navController: NavHostController, viewModel: LoginViewModel, mainViewModel: MainViewModel
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.value.state) {
            is LoadStatus.Init -> {
                viewModel.log("LoginScreen", "Init")
                OutlinedTextField(value = state.value.userName, onValueChange = {
                    viewModel.updateUserName(it)
                }, modifier = Modifier.padding(16.dp), label = { Text("Username") })

                OutlinedTextField(value = state.value.passWord, onValueChange = {
                    viewModel.updatePassword(it)
                }, modifier = Modifier.padding(16.dp), label = { Text("Password") })

                ElevatedButton(onClick = {
                    viewModel.login()
                }) {
                    Text(text = "Login")
                }
            }

            is LoadStatus.Error -> {
                mainViewModel.setError(state.value.state.description)
                viewModel.reset()
            }

            is LoadStatus.Loading -> {
                CircularProgressIndicator()
            }

            is LoadStatus.Success -> {
                //navigate to HomeScreen
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Home.route)
                }
            }
        }
    }
}