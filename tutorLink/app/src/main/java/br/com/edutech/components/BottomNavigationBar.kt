package br.com.edutech.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.edutech.R

@Composable
fun BottomNavigationBar(selectedTab: Int, navController: NavController) {
    Surface(
        shadowElevation = 8.dp,
        color = colorResource(id = R.color.beige)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavigationItem(
                icon = Icons.Default.Person,
                isSelected = selectedTab == 0,
                onSelected = { navController.navigate("edit") }
            )
            BottomNavigationItem(
                icon = Icons.Default.Home,
                isSelected = selectedTab == 1,
                onSelected = { navController.navigate("main") }
            )
            BottomNavigationItem(
                icon = Icons.Default.Favorite,
                isSelected = selectedTab == 2,
                onSelected = { navController.navigate("matches") }
            )
        }
    }
}

@Composable
fun BottomNavigationItem(icon: ImageVector, isSelected: Boolean, onSelected: () -> Unit) {
    IconButton(onClick = onSelected) {
        val tint = if (isSelected) colorResource(id = R.color.brown) else colorResource(id = R.color.white)
        Icon(
            imageVector = icon,
            contentDescription = null, // Provide a localized description if needed
            tint = tint
        )
    }
}