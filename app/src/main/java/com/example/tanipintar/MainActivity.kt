package com.example.tanipintar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tanipintar.ui.theme.screens.HomeScreen
import com.example.tanipintar.ui.theme.screens.DetailScreen
import com.example.tanipintar.ui.theme.TaniPintarTheme
import com.example.tanipintar.viewmodel.TanamanViewModel
import androidx.navigation.NavController
import com.example.tanipintar.navigation.AppNavigation

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanipintar.viewmodel.AktivitasViewModel
import com.example.tanipintar.viewmodel.CatatanViewModel
import com.example.tanipintar.viewmodel.PekerjaViewModel


class MainActivity : ComponentActivity() {
    private val tanamanViewModel: TanamanViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaniPintarTheme {
//                val navController = rememberNavController()
                val viewModel = TanamanViewModel()
                val pekerjaViewModel: PekerjaViewModel = viewModel()
                val catatanViewModel: CatatanViewModel = viewModel()
                val aktivitasViewModel: AktivitasViewModel = viewModel()
                AppNavigation(viewModel = viewModel,
                    pekerjaViewModel = pekerjaViewModel,
                    catatanViewModel = catatanViewModel,
                aktivitasViewModel = aktivitasViewModel)
            }
        }
    }
}

// Preview fungsi tunggal untuk keseluruhan aplikasi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaniPintarTheme {
        AppNavigation(TanamanViewModel(), PekerjaViewModel(),CatatanViewModel(),AktivitasViewModel())
    }
}