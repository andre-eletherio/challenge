package br.com.edutech.screens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.edutech.components.Container
import br.com.edutech.components.CustomSpacer
import br.com.edutech.components.Input
import br.com.edutech.components.MainButton
import br.com.edutech.components.SubButton
import br.com.edutech.components.Title
import br.com.edutech.model.RegisterUser
import br.com.edutech.model.User
import br.com.edutech.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var email = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }

    var error = remember {
        mutableStateOf("")
    }

    fun signIn() {
        if (email.value.isNotBlank() && password.value.isNotBlank()) {
            var call = RetrofitFactory().userService().getUser(email = email.value)
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    Log.i("consolelog", "onResponse: ${response.body()}")
                    if (response.code() == 200) {
                        if (response.body() !== null && response.body()!!.isNotEmpty()) {
                            if (response.body()?.get(0)?.password == password.value) {
                                navController.navigate(("main"))
                            } else {
                                error.value = "Verifique seus dados"
                            }
                        } else {
                            error.value = "Verifique seus dados"
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.i("RegisterScreen", "Error", t)
                }
            })
        } else {
            error.value = "Preencha todos os campos"
        }
    }

    Container {
        Title(text = "Entrar")

        CustomSpacer(space = 50.dp)

        Input(value = email.value, onValueChange = { email.value = it }, label = "E-mail", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)

        Input(value = password.value, onValueChange = { password.value = it }, label = "Senha", leadingIcon = Icons.Default.Lock, keyboardType = KeyboardType.Password)

        if (error.value.length > 0) {
            Text(text = error.value)
        }

        MainButton(onclick = { signIn() }, text = "Entrar")

        CustomSpacer(space = 40.dp)

        SubButton(onclick = { navController.navigate("register") }, text = "Cadastre-se")
    }
}