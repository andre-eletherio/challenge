package br.com.edutech.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.edutech.R
import br.com.edutech.components.BottomNavigationBar
import br.com.edutech.components.CustomSpacer
import br.com.edutech.components.ScreenWithNav
import br.com.edutech.components.SubButton

@Composable
fun EditScreen(navController: NavController) {

    ScreenWithNav(selectedTab = 0, navController = navController) {
        CustomSpacer(space = 20.dp)
        SubButton(onclick = { navController.navigate("login") }, text = "Sair")
    }
}