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
import com.example.tanipintar.model.Pekerja
import com.example.tanipintar.viewmodel.PekerjaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PekerjaScreen(navController: NavController, viewModel: PekerjaViewModel) {


    val pekerjaList = viewModel.pekerjaList.collectAsState(initial = emptyList())
    val message by viewModel.message.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_edit2") }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah pekerja")
            }
        },
        topBar = {
            TopAppBar(title = { Text("Pekerja") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (pekerjaList.value.isEmpty()) {
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
                    items(pekerjaList.value) { data ->
                        pekerjaItem(data = data, navController = navController)
                    }
                }
            }
        }
    }
}

//

@Composable
 fun pekerjaItem(data: Pekerja, navController: NavController) {
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
                Text(text = data.nama_pekerja, style = MaterialTheme.typography.titleMedium)
                Text(text = "jabatan: ${data.jabatan}", style = MaterialTheme.typography.bodySmall)
                Text(text = "kontak: ${data.kontak}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { navController.navigate("add_edit2/${data.id_pekerja}") }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit pekerja")
            }
        }
    }
}
