package com.jin.notemanagerjc.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jin.notemanagerjc.MainViewModel
import com.jin.notemanagerjc.Screen
import com.jin.notemanagerjc.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController, viewModel: HomeViewModel, mainViewModel: MainViewModel, index: Int
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Detail")
        })
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DetailNote(viewModel, index, navController)
        }
    }
}

@Composable
fun DetailNote(
    viewModel: HomeViewModel,
    index: Int,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState()
    if (index >= 0) {
        val note = state.value.notes[index]
        Column {
            Text("DateTime: ${note.title}")
            Spacer(Modifier.padding(10.dp))
            Text("Title: ${note.title}")
            Spacer(Modifier.padding(10.dp))
            Text("Content: ${note.content}")
            Spacer(Modifier.padding(10.dp))
            ElevatedButton(onClick = {
                navController.navigate("${Screen.AddOrEdit.route}?noteIndex=$index")
            }) {
                Text("Edit")
            }
        }
    }
}