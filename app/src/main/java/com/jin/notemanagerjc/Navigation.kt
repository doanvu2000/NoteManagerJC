package com.jin.notemanagerjc

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jin.notemanagerjc.ui.screens.addoredit.AddOrEditScreen
import com.jin.notemanagerjc.ui.screens.addoredit.AddOrEditViewModel
import com.jin.notemanagerjc.ui.screens.detail.DetailScreen
import com.jin.notemanagerjc.ui.screens.home.HomeScreen
import com.jin.notemanagerjc.ui.screens.home.HomeViewModel
import com.jin.notemanagerjc.ui.screens.login.LoginScreen

sealed class Screen(val route: String) {
    object Login : Screen("Login")
    object Home : Screen("Home")
    object Detail : Screen("Detail")
    object AddOrEdit : Screen("AddOrEdit")
}

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error != "") {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_SHORT).show()
            mainViewModel.setError("")
        }
    }

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavHost(navController = navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                LoginScreen(navController, hiltViewModel(), mainViewModel)
            }

            composable(Screen.Home.route) {
                HomeScreen(navController, hiltViewModel(), mainViewModel)
            }

            composable(
                Screen.Detail.route + "?noteIndex={noteIndex}", arguments = listOf(
                    navArgument("noteIndex") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                it.arguments?.getInt("noteIndex")?.let { index ->
                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(Screen.Home.route)
                    }
                    val homeViewModel: HomeViewModel = hiltViewModel(parentEntry)
                    DetailScreen(navController, homeViewModel, mainViewModel, index)
                }
            }

            composable(
                Screen.AddOrEdit.route + "?noteIndex={noteIndex}", arguments = listOf(
                    navArgument("noteIndex") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                it.arguments?.getInt("noteIndex")?.let { index ->
                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(Screen.Home.route)
                    }
                    val homeViewModel: HomeViewModel = hiltViewModel(parentEntry)
                    AddOrEditScreen(
                        navController, homeViewModel, hiltViewModel<AddOrEditViewModel>
                            (), mainViewModel, index
                    )
                }
            }
        }
    }
}