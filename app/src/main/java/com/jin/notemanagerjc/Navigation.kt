package com.jin.notemanagerjc

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jin.notemanagerjc.ui.screens.login.LoginScreen

sealed class Screen(val route: String) {
    object Login : Screen("Login")
    object Home : Screen("Home")
    object Detail : Screen("Detail")
    object AddOrEdit : Screen("AddOrEdit")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController, hiltViewModel(), mainViewModel)
        }
    }
}