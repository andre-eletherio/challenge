package br.com.edutech.screens

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.edutech.R
import br.com.edutech.components.CustomSpacer
import br.com.edutech.components.Input
import br.com.edutech.components.MainButton
import br.com.edutech.components.ScreenWithNav
import br.com.edutech.components.Select
import br.com.edutech.components.SubButton
import br.com.edutech.components.SwipeCard
import br.com.edutech.model.RegisterLike
import br.com.edutech.model.RegisterUser
import br.com.edutech.model.User
import br.com.edutech.service.RetrofitFactory
import br.com.edutech.utils.NotificationHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MainScreen(navController: NavController) {
    var showFilter = remember { mutableStateOf(false) }

    var degree: MutableState<String?> = remember { mutableStateOf(null) }
    var availability: MutableState<String?> = remember { mutableStateOf(null) }

    ScreenWithNav(selectedTab = 1, navController = navController) {

        if (!showFilter.value) {
            Match(showFilter, degree, availability)
        } else {
            Filter(showFilter, degree, availability)
        }
    }
}

@Composable
fun Filter(showFilter: MutableState<Boolean>, degree: MutableState<String?>, availability: MutableState<String?>) {
    var expanded1 = remember { mutableStateOf(false) }
    var selectedOption1 = remember { mutableStateOf(
        if (degree.value.isNullOrEmpty())
            "Escolaridade"
        else
            degree.value!!
    )}
    var options1 = listOf<String>("Nível Médio", "Nível Superior", "Mestrado", "Doutorado")

    var options2 = listOf<String>("Sim", "Não")

    var expanded3 = remember { mutableStateOf(false) }
    var selectedOption3 = remember { mutableStateOf(
        if (availability.value.isNullOrEmpty())
            "Disponibilidade por semana"
        else
            availability.value!!
    )}
    var options3 = listOf<String>("1", "2", "3", "4", "5", "6", "7")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Select(
            expanded = expanded1,
            selectedOption = selectedOption1,
            options = options1,
            value = degree
        )
        Select(
            expanded = expanded3,
            selectedOption = selectedOption3,
            options = options3,
            value = availability
        )
        CustomSpacer(space = 20.dp)
        SubButton(onclick = { showFilter.value = false }, text = "Filtrar")
        CustomSpacer(space = 20.dp)
        SubButton(onclick = {
            degree.value = null
            availability.value = null
            showFilter.value = false
        }, text = "Remover filtros")
        CustomSpacer(space = 20.dp)
    }
}

@Composable
fun Match(showFilter: MutableState<Boolean>, degree: MutableState<String?>, availability: MutableState<String?>) {
    val context = LocalContext.current
    val direction = remember { mutableStateOf<Boolean>(false) }

    val isLoading = remember {
        mutableStateOf<Boolean>(false)
    }

    val user = remember {
        mutableStateOf<User?>(null)
    }

    fun getProfile() {
        isLoading.value = true
        var call = RetrofitFactory().userService().getUser(seen = false, degree = degree.value, availability = availability.value?.toInt())
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.i("consolelog", "onResponse: ${response.body()}")
                if (response.code() == 200) {
                    val users = response.body()
                    if (!users.isNullOrEmpty()) {
                        user.value = users[0]
                    } else {
                        user.value = null
                    }
                } else {
                    user.value = null
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
        getProfile()
    }

    fun swipe(side: Boolean, context: Context) {
        val notificationHandler = NotificationHandler(context)

        val id = user.value?.id

        if (id !== null) {
            val user = if (side) {
                User(liked = true, seen = true)
            } else {
                User(seen = true)
            }

            var call = RetrofitFactory().userService().updateUser(id, user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.i("consolelog", "onResponse: ${response.body()}")
                    if (side) {
                        notificationHandler.showSimpleNotification()
                    }
                    getProfile()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("consolelog", "Error", t)
                    getProfile()
                }
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (user.value !== null) {
            SwipeCard(
                onSwipeLeft = { swipe(true, context) },
                onSwipeRight = { swipe(false, context) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .background(colorResource(id = R.color.beige))
                        .padding(horizontal = 20.dp, vertical = 35.dp),

                ) {
                    Column (
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = user.value?.name ?: "Sem nome",
                            color = Color.Black,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomSpacer(space = 10.dp)
                        Text(
                            text = user.value?.degree ?: "Sem Escolaridade",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomSpacer(space = 10.dp)
                        Text(
                            text = user.value?.location ?: "Sem Localização",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomSpacer(space = 50.dp)
                        Text(
                            text = user.value?.description ?: "Sem Descrição",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomSpacer(space = 40.dp)
                        if (user.value != null) {
                            InterestsMain(user = user.value!!)
                        }
                    }
                }
            }
        } else {
            if (isLoading.value) {
                Text(
                    text = "Carregando...",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Não há usuários",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        CustomSpacer(space = 30.dp)

        SubButton(
            onclick = { showFilter.value = true }, text = "Filtrar"
        )
    }
}

@Composable
fun InterestItem(
    text: String
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = colorResource(id = R.color.brown), fontSize = 14.sp)
    }
}

@Composable
fun InterestsMain(user: User) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        user.interests?.forEach { item ->
            InterestItem(
                text = item
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TestNotif(context: Context) {
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    Column {
        Button(onClick = {
            notificationHandler.showSimpleNotification()
        }) {
            Text(text = "Simple notification")
        }
    }
}