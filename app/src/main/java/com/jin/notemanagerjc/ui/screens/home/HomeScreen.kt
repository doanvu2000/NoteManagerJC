package com.jin.notemanagerjc.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jin.notemanagerjc.MainViewModel
import com.jin.notemanagerjc.Screen
import com.jin.notemanagerjc.common.enumz.LoadStatus
import com.jin.notemanagerjc.common.util.toTimeFormat
import com.jin.notemanagerjc.ui.screens.detail.DetailNote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: HomeViewModel, mainViewModel: MainViewModel
) {
    val state = viewModel.uiState.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Note Manager") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.AddOrEdit.route)
        }) {
            Icon(Icons.Default.Add, "Add Note")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.value.status) {
                is LoadStatus.Error -> {
                    mainViewModel.setError(state.value.status.description)
                    viewModel.reset()
                }

                is LoadStatus.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.Red)
                    }
                }

                is LoadStatus.Init, is LoadStatus.Success -> {
                    if (screenWidth < 600.dp) {
                        ListNote(state, navController, viewModel, false)
                    } else {
                        Row {
                            Box(modifier = Modifier.weight(1f)) {
                                ListNote(state, navController, viewModel, true)
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                DetailNote(viewModel, state.value.selectedIndex, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListNote(
    state: State<HomeUiState>,
    navController: NavHostController,
    viewModel: HomeViewModel,
    isSpitMode: Boolean
) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(state.value.notes.size) { index ->
            val note = state.value.notes[index]
            ListItem(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        if (!isSpitMode) {
                            navController.navigate(
                                "${Screen.Detail.route}?noteIndex=${index}"
                            )
                        } else {
                            viewModel.selectNote(index)
                        }
                    }, overlineContent = {
                    Text(note.dateTime.toTimeFormat())
                }, headlineContent = {
                    Text(note.title)
                }, supportingContent = {
                    Text(note.content)
                }, trailingContent = {
                    IconButton(onClick = {
                        viewModel.deleteNote(note.dateTime)
                    }) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
                })
        }
    }
}