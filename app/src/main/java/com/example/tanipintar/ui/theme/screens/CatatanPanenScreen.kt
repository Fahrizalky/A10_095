package com.example.tanipintar.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
import com.example.tanipintar.model.CatatanPanen
import com.example.tanipintar.viewmodel.CatatanViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatatanPanenScreen(navController: NavController, viewModel: CatatanViewModel) {


    val catatanList = viewModel.catatanList.collectAsState(initial = emptyList())
    val message by viewModel.message.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_edit3") }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah catatan")
            }
        },
        topBar = {
            TopAppBar(title = { Text("Catatan Panen") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (catatanList.value.isEmpty()) {
                    item {
                        Text(
                            text = "Tidak ada data catatan",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(catatanList.value) { data ->
                        catatanItem(data = data, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
 fun catatanItem(data: CatatanPanen, navController: NavController) {
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
                Text(text = data.id_panen, style = MaterialTheme.typography.titleMedium)
                Text(text = "Tanggal: ${data.tanggal_panen}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Jumlah Hasil: ${data.jumlah_hasil}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Keterangan: ${data.keterangan}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { navController.navigate("add_edit3/${data.id_panen}") }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit catatan")
            }
        }
    }
}
