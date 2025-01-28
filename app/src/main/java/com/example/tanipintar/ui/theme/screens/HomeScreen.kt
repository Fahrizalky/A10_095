package com.example.tanipintar.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tanipintar.viewmodel.TanamanViewModel

import com.example.tanipintar.model.DataItem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TanamanViewModel) {


    val tanamanList = viewModel.tanamanList.collectAsState(initial = emptyList())
    val message by viewModel.message.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_edit") }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Tanaman")
            }
        },
        topBar = {
            TopAppBar(title = { Text("UAS PAM") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (tanamanList.value.isEmpty()) {
                    item {
                        Text(
                            text = "Tidak ada data tanaman",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(tanamanList.value) { data ->
                        TanamanItem(data = data, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
 fun TanamanItem(data: DataItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = data.nama_tanaman, style = MaterialTheme.typography.titleMedium)
                Text(text = "Periode: ${data.periode_tanam}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Deskripsi: ${data.deskripsi_tanaman}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { navController.navigate("add_edit/${data.id_tanaman}") }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Tanaman")
            }
        }
    }
}
