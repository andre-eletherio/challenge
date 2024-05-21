package br.com.edutech

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.edutech.screens.EditScreen
import br.com.edutech.screens.LoginScreen
import br.com.edutech.screens.MainScreen
import br.com.edutech.screens.MatchesScreen
import br.com.edutech.screens.ProfileScreen
import br.com.edutech.screens.RegisterScreen
import br.com.edutech.ui.theme.EdutechTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdutechTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.black)),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "register")
                    {
                        composable(route = "register"){ RegisterScreen(navController) }
                        composable(route = "login"){ LoginScreen(navController) }
                        composable(route = "profile/{id}"){ navBackStackEntry ->
                            val id = navBackStackEntry.arguments?.getString("id", "")
                            ProfileScreen(navController, id ?: "")
                        }
                        composable(route = "main"){ MainScreen(navController) }
                        composable(route = "edit"){ EditScreen(navController) }
                        composable(route = "matches"){ MatchesScreen(navController) }
                    }
                }
            }
        }
    }
}