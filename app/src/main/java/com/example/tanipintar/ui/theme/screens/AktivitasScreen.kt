package com.example.tanipintar.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tanipintar.model.Aktivitas
import com.example.tanipintar.model.Pekerja
import com.example.tanipintar.viewmodel.AktivitasViewModel
import com.example.tanipintar.viewmodel.PekerjaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AktivitasScreen(navController: NavController, viewModel: AktivitasViewModel) {


    val aktivitasList = viewModel.aktivitasList.collectAsState(initial = emptyList())
    val message by viewModel.message.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_edit4") }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah pekerja")
            }
        },
        topBar = {
            TopAppBar(title = { Text("Aktivitas") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (aktivitasList.value.isEmpty()) {
                    item {
                        Text(
                            text = "Tidak ada data pekerja",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(aktivitasList.value) { data ->
                        aktivitasItem(data = data, navController = navController)
                    }
                }
            }
        }
    }
}
//
@Composable
fun aktivitasItem(data: Aktivitas, navController: NavController) {
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
                Text(text = data.id_tanaman, style = MaterialTheme.typography.titleMedium)
                Text(text = "id pekerja: ${data.id_pekerja}", style = MaterialTheme.typography.bodySmall)
                Text(text = "tgl: ${data.tanggal_aktivitas}", style = MaterialTheme.typography.bodySmall)
                Text(text = "tgl: ${data.deskripsi_aktivitas}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { navController.navigate("add_edit4/${data.id_aktivitas}") }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit pekerja")
            }
        }
    }
}
