package com.example.tanipintar.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tanipintar.viewmodel.TanamanViewModel
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
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: TanamanViewModel,
    tanamanId: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var namaTanaman by remember { mutableStateOf("") }
    var periodeTanam by remember { mutableStateOf("") }
    var deskripsiTanaman by remember { mutableStateOf("") }

    val context = LocalContext.current // Pastikan context diambil dari LocalContext
    val calendar = Calendar.getInstance() // Kalender untuk DatePicker


    // Mengisi data jika dalam mode edit
    LaunchedEffect(tanamanId) {
        tanamanId?.let {
            val tanaman = viewModel.getTanamanById(it)
            namaTanaman = tanaman?.nama_tanaman ?: ""
            periodeTanam = tanaman?.periode_tanam ?: ""
            deskripsiTanaman = tanaman?.deskripsi_tanaman ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (tanamanId == null) "Tambah Tanaman" else "Edit Tanaman") },
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
                value = namaTanaman,
                onValueChange = { namaTanaman = it },
                label = { Text("Nama Tanaman") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // OutlinedButton untuk Periode Tanam
            Column {
                Text(text = "", style = MaterialTheme.typography.bodySmall) // Label
                OutlinedButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                periodeTanam = formattedDate
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
                        text = if (periodeTanam.isNotEmpty()) periodeTanam else "Pilih tanggal",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start, // Teks rata kiri agar mirip TextField
                        style = MaterialTheme.typography.bodyLarge.copy(color = if (periodeTanam.isNotEmpty()) Color.White else Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = deskripsiTanaman,
                onValueChange = { deskripsiTanaman = it },
                label = { Text("Deskripsi Tanaman") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    Log.d("AddEditScreen", "Tombol tambah/simpan ditekan")
                    Log.d("AddEditScreen", "Periode Tanam: $periodeTanam")
                    keyboardController?.hide()
                    if (tanamanId == null) {
                        viewModel.insertTanaman(namaTanaman, periodeTanam, deskripsiTanaman)
                    } else {
                        viewModel.updateTanaman(tanamanId, namaTanaman, periodeTanam, deskripsiTanaman)
                    }
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (tanamanId == null) "Tambah" else "Simpan")
            }

            if (tanamanId != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Log.d("AddEditScreen", "Tombol hapus ditekan")
                        Log.d("AddEditScreen", "ID tanaman yang dikirim: $tanamanId")
                        val idTanaman = tanamanId.toInt()
                        viewModel.deleteTanaman(idTanaman)
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



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddEditScreen(
//    navController: NavController,
//    viewModel: TanamanViewModel,
//    tanamanId: String? = null
//) {
//    val context = LocalContext.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    var namaTanaman by remember { mutableStateOf("") }
//    var periodeTanam by remember { mutableStateOf("") }
//    var deskripsiTanaman by remember { mutableStateOf("") }
//
//    // Jika ada tanamanId (edit mode), set data awal
//
//    LaunchedEffect(tanamanId) {
//        tanamanId?.let {
//            val tanaman = viewModel.getTanamanById(it)
//            namaTanaman = tanaman?.nama_tanaman ?: ""
//            periodeTanam = tanaman?.periode_tanam ?: ""
//            deskripsiTanaman = tanaman?.deskripsi_tanaman ?: ""
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = if (tanamanId == null) "Tambah Tanaman" else "Edit Tanaman") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Top
//        ) {
//            TextField(
//                value = namaTanaman,
//                onValueChange = { namaTanaman = it },
//                label = { Text("Nama Tanaman") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            TextField(
//                value = periodeTanam,
//                onValueChange = { periodeTanam = it },
//                label = { Text("Periode Tanam") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            TextField(
//                value = deskripsiTanaman,
//                onValueChange = { deskripsiTanaman = it },
//                label = { Text("Deskripsi Tanaman") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    keyboardController?.hide()
//                    if (tanamanId == null) {
//                        viewModel.insertTanaman(namaTanaman, periodeTanam, deskripsiTanaman)
//                    } else {
//                        viewModel.updateTanaman(tanamanId, namaTanaman, periodeTanam, deskripsiTanaman)
//                    }
//                    navController.navigateUp()
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = if (tanamanId == null) "Tambah" else "Simpan")
//            }
//        }
//    }
//}
