package com.example.tanipintar.data

import com.example.tanipintar.model.DataItem

fun sampleData(): List<DataItem> {
    return listOf(
        DataItem(1.toString(), "Tomat", "Tanaman tomat cocok ditanam di lahan yang subur.",""),
        DataItem(2.toString(), "Cabai", "Cabai adalah tanaman tropis yang membutuhkan banyak sinar matahari.",""),
        DataItem(3.toString(), "Padi", "Padi merupakan tanaman pokok yang menghasilkan beras.","")
    )
}

