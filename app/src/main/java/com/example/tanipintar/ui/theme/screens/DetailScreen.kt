package com.example.tanipintar.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tanipintar.viewmodel.TanamanViewModel

@Composable
fun DetailScreen(
    navController: NavController, // Parameter navigasi
    viewModel: TanamanViewModel    // Parameter ViewModel
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Detail Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = { navController.navigate("home") }) {
            Text("Back to Home")
        }
    }
}
