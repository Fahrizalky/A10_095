package com.example.tanipintar.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tanipintar.viewmodel.PekerjaViewModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import com.example.tanipintar.ui.theme.screens.DatePickerField

import androidx.compose.material.icons.filled.CalendarMonth
import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.tanipintar.viewmodel.AktivitasViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAktivitas(
    navController: NavController,
    viewModel: AktivitasViewModel,
    aktivitasId: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var id_tanaman by remember { mutableStateOf("") }
    var id_pekerja by remember { mutableStateOf("") }
    var tanggal_aktivitas by remember { mutableStateOf("") }
    var deskripsi_aktivitas by remember { mutableStateOf("") }

    val context = LocalContext.current // Pastikan context diambil dari LocalContext
    val calendar = Calendar.getInstance() // Kalender untuk DatePicker


    // Mengisi data jika dalam mode edit
    LaunchedEffect(aktivitasId) {
        aktivitasId?.let {
            val aktivitas = viewModel.getaktivitasById(it)
            id_tanaman = aktivitas?.id_tanaman ?: ""
            id_pekerja = aktivitas?.id_pekerja ?: ""
            tanggal_aktivitas = aktivitas?.tanggal_aktivitas ?: ""
            deskripsi_aktivitas = aktivitas?.deskripsi_aktivitas ?: ""
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (aktivitasId == null) "Tambah aktivitas" else "Edit aktivitas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = id_tanaman,
                onValueChange = { id_tanaman = it },
                label = { Text("tanaman id") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = id_pekerja,
                onValueChange = { id_pekerja = it },
                label = { Text("pekerja id") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column {
                Text(text = "", style = MaterialTheme.typography.bodySmall) // Label
                OutlinedButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                tanggal_aktivitas = formattedDate
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // Tinggi tombol agar konsisten dengan TextField
                    shape = MaterialTheme.shapes.small, // Membuat outline berbentuk kotak
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), // Outline agar serupa TextField
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent, // Latar belakang transparan
                        contentColor = MaterialTheme.colorScheme.onPrimary // Warna teks
                    )
                ) {
                    Text(
                        text = if (tanggal_aktivitas.isNotEmpty()) tanggal_aktivitas else "Pilih tanggal",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start, // Teks rata kiri agar mirip TextField
                        style = MaterialTheme.typography.bodyLarge.copy(color = if (tanggal_aktivitas.isNotEmpty()) Color.White else Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
//            TextField(
//                value = tanggal_aktivitas,
//                onValueChange = { tanggal_aktivitas = it },
//                label = { Text("tanggal") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = deskripsi_aktivitas,
                onValueChange = { deskripsi_aktivitas = it },
                label = { Text("Deskripsi aktivitas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    Log.d("AddEditPekerja", "Tombol tambah/simpan ditekan")
                    keyboardController?.hide()
                    if (aktivitasId == null) {
                        viewModel.insertaktivitas(id_tanaman, id_pekerja, tanggal_aktivitas, deskripsi_aktivitas)
                    } else {
                        viewModel.updateaktivitas(aktivitasId, id_tanaman, id_pekerja, tanggal_aktivitas, deskripsi_aktivitas)
                    }
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (aktivitasId == null) "Tambah" else "Simpan")
            }

            if (aktivitasId != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Log.d("AddEditScreen", "Tombol hapus ditekan")
//                        Log.d("AddEditScreen", "ID pekerja yang dikirim: $pekerjaId")
                        val idAktivitas = aktivitasId.toInt()
                        viewModel.deleteaktivitas(idAktivitas)
                        navController.navigateUp() // Kembali setelah berhasil menghapus
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Red, Color.White // Warna latar belakang tombol
                        // Warna teks tombol
                    )
                ) {
                    Text(text = "Hapus", color = Color.White)
                }
            }
        }
    }
}


