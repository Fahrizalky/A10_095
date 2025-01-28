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
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPekerja(
    navController: NavController,
    viewModel: PekerjaViewModel,
    pekerjaId: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var namaPekerja by remember { mutableStateOf("") }
    var jabatan by remember { mutableStateOf("") }
    var kontak by remember { mutableStateOf("") }

    val context = LocalContext.current // Pastikan context diambil dari LocalContext
    val calendar = Calendar.getInstance() // Kalender untuk DatePicker


    // Mengisi data jika dalam mode edit
    LaunchedEffect(pekerjaId) {
        pekerjaId?.let {
            val pekerja = viewModel.getpekerjaById(it)
            namaPekerja = pekerja?.nama_pekerja ?: ""
            jabatan = pekerja?.jabatan ?: ""
            kontak = pekerja?.kontak ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (pekerjaId == null) "Tambah pekerja" else "Edit pekerja") },
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
                value = namaPekerja,
                onValueChange = { namaPekerja = it },
                label = { Text("Nama pekerja") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = jabatan,
                onValueChange = { jabatan = it },
                label = { Text("Jabatan") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = kontak,
                onValueChange = { kontak = it },
                label = { Text("Deskripsi pekerja") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    Log.d("AddEditPekerja", "Tombol tambah/simpan ditekan")
                    keyboardController?.hide()
                    if (pekerjaId == null) {
                        viewModel.insertpekerja(namaPekerja, jabatan, kontak)
                    } else {
                        viewModel.updatepekerja(pekerjaId, namaPekerja, jabatan, kontak)
                    }
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (pekerjaId == null) "Tambah" else "Simpan")
            }

            if (pekerjaId != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Log.d("AddEditScreen", "Tombol hapus ditekan")
                        Log.d("AddEditScreen", "ID pekerja yang dikirim: $pekerjaId")
                        val idPekerja = pekerjaId.toInt()
                        viewModel.deletepekerja(idPekerja)
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


