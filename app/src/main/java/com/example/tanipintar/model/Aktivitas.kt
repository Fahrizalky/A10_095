package com.example.tanipintar.model

data class Aktivitas(
    val id_aktivitas: String,
    val id_tanaman: String,
    val id_pekerja: String,
    val tanggal_aktivitas: String,
    val deskripsi_aktivitas: String
)