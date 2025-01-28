package com.example.tanipintar.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanipintar.R
import com.example.tanipintar.viewmodel.TanamanViewModel


@Composable
fun UtamaScreen(navController: NavController, viewModel: TanamanViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selamat Datang UAS PAM",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Grid Layout for menu
        val menuItems = listOf(
            MenuItem("Tanaman", R.drawable.ic_plant, "tanaman"),
            MenuItem("Pekerja", R.drawable.ic_plant, "pekerja"),
            MenuItem("Aktivitas", R.drawable.ic_plant, "aktivitas"),
            MenuItem("Catatan Panen", R.drawable.ic_plant, "catatan_panen")
        )

        val rows = menuItems.chunked(2) // 2 items per row

        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { menuItem ->
                    MenuCard(menuItem, navController)
                }
            }
        }
    }
}


data class MenuItem(
    val label: String,
    val iconRes: Int,
    val route: String
)

@Composable
fun MenuCard(item: MenuItem, navController: NavController) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp)
            .clickable { navController.navigate(item.route) },
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.label,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = item.label,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



