package com.jin.notemanagerjc.ui.screens.addoredit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jin.notemanagerjc.MainViewModel
import com.jin.notemanagerjc.common.enumz.LoadStatus
import com.jin.notemanagerjc.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    addOrEditViewModel: AddOrEditViewModel,
    mainViewModel: MainViewModel,
    index: Int
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val state = addOrEditViewModel.uiState.collectAsState()

    if (index >= 0) {
        val note = homeViewModel.uiState.value.notes[index]
        title = note.title
        content = note.content
    }

    LaunchedEffect(state.value.status) {
        if (state.value.status is LoadStatus.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (index == -1) "Add" else "Edit")
            })
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.value.status) {
                is LoadStatus.Error -> {
                    mainViewModel.setError(state.value.status.description)
                    addOrEditViewModel.reset()
                }

                is LoadStatus.Loading -> {
                    CircularProgressIndicator(color = Color.Red)
                }

                is LoadStatus.Init, is LoadStatus.Success -> {
                    OutlinedTextField(value = title, onValueChange = {
                        title = it
                    }, label = { Text("Title") })

                    Spacer(Modifier.padding(16.dp))

                    OutlinedTextField(value = content, onValueChange = {
                        content = it
                    }, label = { Text("Content") })

                    Spacer(Modifier.padding(16.dp))

                    ElevatedButton(onClick = {
                        if (index == -1) {
                            addOrEditViewModel.addNote(title, content)
                        } else {
                            addOrEditViewModel.editNote(
                                homeViewModel.uiState.value.notes[index].dateTime,
                                title,
                                content
                            )
                        }
                    }) {
                        Text(if (index == -1) "Add" else "Save")
                    }
                }
            }
        }
    }
}