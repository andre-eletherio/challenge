package br.com.edutech.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.edutech.components.CustomSpacer
import br.com.edutech.components.MatchCard
import br.com.edutech.components.ScreenWithNav
import br.com.edutech.model.User
import br.com.edutech.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MatchesScreen(navController: NavController) {

    val matches = remember {
        mutableStateOf<List<User>?>(listOf())
    }

    val isLoading = remember {
        mutableStateOf<Boolean>(false)
    }

    fun getLikes() {
        isLoading.value = true
        var call = RetrofitFactory().userService().getUser(liked = true)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.i("consolelog", "onResponse: ${response.body()}")
                if (response.code() == 200) {
                    val users = response.body()
                    if (!users.isNullOrEmpty()) {
                        matches.value = users
                    }
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i("RegisterScreen", "Error", t)
                isLoading.value = false
            }
        })
    }

    LaunchedEffect(key1 = Unit) {
        getLikes()
    }

    ScreenWithNav(2, navController = navController) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (matches.value?.isEmpty() == true) {
                if (isLoading.value) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        text = "Carregando...",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        text = "Você ainda não possui matches :(",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            matches.value?.forEach{ match ->
                MatchCard(match)
            }
            CustomSpacer(space = 80.dp)
        }
    }
}