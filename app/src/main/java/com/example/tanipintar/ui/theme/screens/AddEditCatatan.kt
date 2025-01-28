package com.example.tanipintar.ui.theme.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.tanipintar.model.DataItem
import com.example.tanipintar.viewmodel.CatatanViewModel
import java.util.Calendar
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlin.math.log

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCatatan(
    navController: NavController,
    viewModel: CatatanViewModel,
    tanamanViewModel: TanamanViewModel,
    panenId: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var idTanaman by remember { mutableStateOf("") }
    var tanggalPanen by remember { mutableStateOf("") }
    var jumlahHasil by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Isi data jika dalam mode edit
    LaunchedEffect(panenId) {
        panenId?.let {
            val catatan = viewModel.getcatatanById(it)
            idTanaman = catatan?.id_tanaman ?: ""
            tanggalPanen = catatan?.tanggal_panen ?: ""
            jumlahHasil = catatan?.jumlah_hasil ?: ""
            keterangan = catatan?.keterangan ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { androidx.compose.material3.Text(text = if (panenId == null) "Tambah Catatan" else "Edit Catatan") },
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

//            var expanded by remember { mutableStateOf(false) } // Untuk mengontrol visibilitas dropdown
//            var selectedTanaman by remember { mutableStateOf("") } // Untuk menyimpan opsi yang dipilih
//            val listTanaman = listOf("Tanaman A", "Tanaman B", "Tanaman C") // Opsi dropdown
//
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded } // Toggle dropdown
//            ) {
//                OutlinedTextField(
//                    value = selectedTanaman,
//                    onValueChange = {},
//                    readOnly = true, // Supaya tidak bisa diinput manual
//                    label = { Text("Tanaman") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor(), // Mengaitkan TextField dengan dropdown
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                    },
//                    colors = ExposedDropdownMenuDefaults.textFieldColors() // Warna default
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false } // Tutup dropdown jika diklik di luar
//                ) {
//                    listTanaman.forEach { tanaman ->
//                        DropdownMenuItem(
//                            text = { Text(tanaman) },
//                            onClick = {
//                                selectedTanaman = tanaman // Simpan opsi yang dipilih
//                                expanded = false // Tutup dropdown
//                            }
//                        )
//                    }
//                }
//            }


            TextField(
                value = idTanaman,
                onValueChange = { idTanaman = it },
                label = { Text("Tanaman") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

//            var selectedTanaman by remember { mutableStateOf<DataItem?>(null) }
//            var expanded by remember { mutableStateOf(false) }
//            val tanamanList = tanamanViewModel.tanamanList.collectAsState()
//            Log.d("AddEditCatatan", "Tanaman List: ${tanamanList.value}")
////             Dropdown Pilih Tanaman
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded }
//            ) {
//                TextField(
//                    value = selectedTanaman?.nama_tanaman ?: "Pilih Tanaman",
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text("Tanaman") },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.textFieldColors(
//                        containerColor = Color.Transparent
//                    )
//                )
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                    if (tanamanList.value.isNotEmpty()) {
//                        tanamanList.value.forEach { data ->
//                            DropdownMenuItem(
//                                onClick = {
//                                    selectedTanaman = data
//                                    idTanaman = data.id_tanaman
//                                    expanded = false
//                                }
//                            ) {
//                                Text(text = data.nama_tanaman)
//                            }
//                        }
//                    } else {
//                        DropdownMenuItem(
//                            onClick = { expanded = false }
//                        ) {
//                            Text("Daftar tanaman kosong")
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))

            // Tanggal Panen
            Column {
                Text(text = "", style = MaterialTheme.typography.bodySmall)
                OutlinedButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                                tanggalPanen = formattedDate
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.small,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = if (tanggalPanen.isNotEmpty()) tanggalPanen else "Pilih tanggal",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (tanggalPanen.isNotEmpty()) Color.White else Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Jumlah Hasil
            TextField(
                value = jumlahHasil,
                onValueChange = { jumlahHasil = it },
                label = { Text("Jumlah Hasil") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Keterangan
            TextField(
                value = keterangan,
                onValueChange = { keterangan = it },
                label = { Text("Keterangan") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Tambah/Simpan
            Button(
                onClick = {
                    keyboardController?.hide()
                    if (idTanaman.isEmpty() || tanggalPanen.isEmpty() || jumlahHasil.isEmpty()) {
                        Log.d("AddEditCatatan", "Pastikan semua data terisi")
                        return@Button
                    }

                    if (panenId == null) {
                        viewModel.insertcatatan(idTanaman, tanggalPanen, jumlahHasil, keterangan)
                    } else {
                        viewModel.updatecatatan(panenId, idTanaman, tanggalPanen, jumlahHasil, keterangan)
                    }
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (panenId == null) "Tambah" else "Simpan")
            }

            // Tombol Hapus
            if (panenId != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.deletecatatan(panenId.toInt())
                        navController.navigateUp()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Red, Color.White
                    )
                ) {
                    Text("Hapus")
                }
            }
        }
    }
}



//
//@SuppressLint("StateFlowValueCalledInComposition")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddEditCatatan(
//    navController: NavController,
//    viewModel: CatatanViewModel,
//    tanamanViewModel: TanamanViewModel,
//    panenId: String? = null
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    var idTanaman by remember { mutableStateOf("") }
//    var tanggalPanen by remember { mutableStateOf("") }
//    var jumlahHasil by remember { mutableStateOf("") }
//    var keterangan by remember { mutableStateOf("") }
//
//    val context = LocalContext.current // Pastikan context diambil dari LocalContext
//    val calendar = Calendar.getInstance() // Kalender untuk DatePicker
//
//    var selectedTanaman by remember { mutableStateOf<DataItem?>(null) }
//    var expanded by remember { mutableStateOf(false) }
//    val tanamanList = tanamanViewModel.tanamanList.collectAsState()
//
//    // Mengisi data jika dalam mode edit
//    LaunchedEffect(panenId) {
//        panenId?.let {
//            val catatan = viewModel.getcatatanById(it)
//            idTanaman = catatan?.id_tanaman ?: ""
//            tanggalPanen = catatan?.tanggal_panen ?: ""
//            jumlahHasil= catatan?.jumlah_hasil ?: ""
//            keterangan= catatan?.keterangan ?: ""
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = if (panenId == null) "Tambah pekerja" else "Edit pekerja") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
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
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded }
//            ) {
//                TextField(
//                    value = selectedTanaman?.nama_tanaman ?: "",
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text("Pilih Tanaman") },
//                    modifier = Modifier.fillMaxWidth(),
//
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                        tanamanList.value.forEach { data ->
//                        DropdownMenuItem(
//                            onClick = {
//                                selectedTanaman = data // Menyimpan tanaman yang dipilih
//                                idTanaman = data.id_tanaman.toString() // Hanya menyimpan ID tanaman
//                                expanded = false
//                            },
//                        ) {
//                            Text(data.nama_tanaman) // Sesuaikan nama properti
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
////            TextField(
////                value = idTanaman,
////                onValueChange = { idTanaman = it },
////                label = { Text("Tanaman") },
////                modifier = Modifier.fillMaxWidth()
////            )
////            Spacer(modifier = Modifier.height(10.dp))
//            Column {
//                Text(text = "", style = MaterialTheme.typography.bodySmall) // Label
//                OutlinedButton(
//                    onClick = {
//                        DatePickerDialog(
//                            context,
//                            { _, year, month, dayOfMonth ->
//                                val formattedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year)
//                                tanggalPanen = formattedDate
//                            },
//                            calendar.get(Calendar.YEAR),
//                            calendar.get(Calendar.MONTH),
//                            calendar.get(Calendar.DAY_OF_MONTH)
//                        ).show()
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp), // Tinggi tombol agar konsisten dengan TextField
//                    shape = MaterialTheme.shapes.small, // Membuat outline berbentuk kotak
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), // Outline agar serupa TextField
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        containerColor = Color.Transparent, // Latar belakang transparan
//                        contentColor = MaterialTheme.colorScheme.onPrimary // Warna teks
//                    )
//                ) {
//                    Text(
//                        text = if (tanggalPanen.isNotEmpty()) tanggalPanen else "Pilih tanggal",
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Start, // Teks rata kiri agar mirip TextField
//                        style = MaterialTheme.typography.bodyLarge.copy(color = if (tanggalPanen.isNotEmpty()) Color.White else Color.Gray)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(10.dp))
////            TextField(
////                value = tanggalPanen,
////                onValueChange = { tanggalPanen = it },
////                label = { Text("Tanggal Panen") },
////                modifier = Modifier.fillMaxWidth()
////            )
////            Spacer(modifier = Modifier.height(10.dp))
//
//            TextField(
//                value = jumlahHasil,
//                onValueChange = { jumlahHasil = it },
//                label = { Text("Jumlah Hasil") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            TextField(
//                value = keterangan,
//                onValueChange = { keterangan = it },
//                label = { Text("Keterangan") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    Log.d("AddEditPekerja", "Tombol tambah/simpan ditekan")
//                    keyboardController?.hide()
//                    if (panenId == null) {
//                        viewModel.insertcatatan(idTanaman, tanggalPanen,jumlahHasil,keterangan)
//                    } else {
//                        viewModel.updatecatatan(panenId,idTanaman, tanggalPanen,jumlahHasil,keterangan)
//                    }
//                    navController.navigateUp()
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = if (panenId == null) "Tambah" else "Simpan")
//            }
//
//            if (panenId != null) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(
//                    onClick = {
//                        Log.d("AddEditScreen", "Tombol hapus ditekan")
//                        Log.d("AddEditScreen", "ID pekerja yang dikirim: $panenId")
//                        val idPanen = panenId.toInt()
//                        viewModel.deletecatatan(idPanen)
//                        navController.navigateUp() // Kembali setelah berhasil menghapus
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(
//                        Color.Red, Color.White // Warna latar belakang tombol
//                        // Warna teks tombol
//                    )
//                ) {
//                    Text(text = "Hapus", color = Color.White)
//                }
//            }
//        }
//    }
//}
//

