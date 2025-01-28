package com.example.tanipintar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.tanipintar.ui.theme.screens.HomeScreen
import com.example.tanipintar.ui.theme.screens.PekerjaScreen
import com.example.tanipintar.viewmodel.TanamanViewModel
import com.example.tanipintar.viewmodel.PekerjaViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tanipintar.ui.theme.screens.AddEditScreen
import androidx.navigation.navArgument
import com.example.tanipintar.ui.theme.screens.AddEditAktivitas
import com.example.tanipintar.ui.theme.screens.AddEditCatatan
import com.example.tanipintar.ui.theme.screens.AddEditPekerja
import com.example.tanipintar.ui.theme.screens.AktivitasScreen
import com.example.tanipintar.ui.theme.screens.CatatanPanenScreen
import com.example.tanipintar.ui.theme.screens.UtamaScreen
import com.example.tanipintar.viewmodel.AktivitasViewModel
import com.example.tanipintar.viewmodel.CatatanViewModel


@Composable
fun AppNavigation(
    viewModel: TanamanViewModel,pekerjaViewModel: PekerjaViewModel,
    catatanViewModel: CatatanViewModel,aktivitasViewModel: AktivitasViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            UtamaScreen(navController = navController, viewModel = viewModel)
        }
        composable("tanaman") {
            // Halaman untuk Tanaman
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "add_edit/{tanamanId}",
            arguments = listOf(navArgument("tanamanId") { nullable = true })
        ) { backStackEntry ->
            val tanamanId = backStackEntry.arguments?.getString("tanamanId")
            AddEditScreen(navController = navController, viewModel = viewModel, tanamanId = tanamanId)
        }
        composable("add_edit") {
            AddEditScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "add_edit2/{pekerjaId}",
            arguments = listOf(navArgument("pekerjaId") { nullable = true })
        ) { backStackEntry ->
            val pekerjaId = backStackEntry.arguments?.getString("pekerjaId")
            AddEditPekerja(navController = navController, viewModel = pekerjaViewModel, pekerjaId = pekerjaId)
        }
        composable("add_edit2") {
            AddEditPekerja(navController = navController, viewModel = pekerjaViewModel)
        }
        composable("pekerja") {
            // Halaman untuk Pekerja
            PekerjaScreen(navController = navController, viewModel = pekerjaViewModel)
        }
        composable("aktivitas") {
            // Halaman untuk Aktivitas
            AktivitasScreen(navController = navController, viewModel = aktivitasViewModel)
        }
        composable("catatan_panen") {
            // Halaman untuk Catatan Panen
            CatatanPanenScreen(navController = navController, viewModel = catatanViewModel)
        }
        composable(
            "add_edit3/{panenId}",
            arguments = listOf(navArgument("panenId") { nullable = true })
        ) { backStackEntry ->
            val panenId = backStackEntry.arguments?.getString("panenId")
            AddEditCatatan(navController = navController, viewModel = catatanViewModel, panenId = panenId,tanamanViewModel = viewModel)
        }
        composable("add_edit3") {
            AddEditCatatan(navController = navController, viewModel = catatanViewModel,tanamanViewModel = viewModel)
        }
        composable(
            "add_edit4/{aktivitasId}",
            arguments = listOf(navArgument("aktivitasId") { nullable = true })
        ) { backStackEntry ->
            val aktivitasId = backStackEntry.arguments?.getString("aktivitasId")
            AddEditAktivitas(navController = navController, viewModel = aktivitasViewModel, aktivitasId = aktivitasId)
        }
        composable("add_edit4") {
            AddEditAktivitas(navController = navController, viewModel = aktivitasViewModel)
        }
    }

}

