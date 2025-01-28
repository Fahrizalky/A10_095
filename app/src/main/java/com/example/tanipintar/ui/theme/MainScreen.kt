package com.example.tanipintar.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanipintar.model.DataItem

@Composable
fun DataItemCard(dataItem: DataItem) {
    // Card untuk Menampilkan Detail Tanaman
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Konten dalam Card
        Column(modifier = Modifier.padding(16.dp)) {
            // ID Tanaman
            Text(
                text = "ID: ${dataItem.id_tanaman}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Nama Tanaman
            Text(
                text = "Nama Tanaman: ${dataItem.nama_tanaman}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Periode Tanam
            Text(
                text = "Periode Tanam: ${dataItem.periode_tanam}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Deskripsi Tanaman
            Text(
                text = "Deskripsi: ${dataItem.deskripsi_tanaman}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

