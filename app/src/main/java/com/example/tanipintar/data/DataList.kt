package com.example.tanipintar.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanipintar.model.DataItem

@Composable
fun DataList(data: List<DataItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(data) { item ->
            DataListItem(item)
        }
    }
}

@Composable
fun DataListItem(item: DataItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.nama_tanaman, style = MaterialTheme.typography.titleMedium)
            Text(text = item.periode_tanam, style = MaterialTheme.typography.bodyMedium)
            Text(text = item.deskripsi_tanaman, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
