package br.com.edutech.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.edutech.components.Container
import br.com.edutech.components.CustomSpacer
import br.com.edutech.components.Input
import br.com.edutech.components.MainButton
import br.com.edutech.components.SelectStyleInput
import br.com.edutech.components.SubButton
import br.com.edutech.components.Title
import br.com.edutech.model.RegisterUser
import br.com.edutech.model.User
import br.com.edutech.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController, id: String) {

    val newItem = remember {
        mutableStateOf("")
    }

    val (items, setItems) = remember { mutableStateOf(mutableListOf<String>()) }

    var name = remember {
        mutableStateOf("")
    }

    var telefone = remember {
        mutableStateOf("")
    }

    var descricao = remember {
        mutableStateOf("")
    }

    var localizacao = remember {
        mutableStateOf("")
    }

    var disponibilidade = remember {
        mutableStateOf("")
    }

    var expanded1 = remember { mutableStateOf(false) }
    var selectedOption1 = remember { mutableStateOf("Escolaridade") }
    var options1 = listOf<String>("Nível Médio", "Nível Superior", "Mestrado", "Doutorado")

    var error = remember {
        mutableStateOf(false)
    }
    
    Container {
        Title(text = "Conte mais sobre você")

        CustomSpacer(space = 50.dp)

        Input(value = name.value, onValueChange = { name.value = it }, label = "Nome", leadingIcon = Icons.Default.Person, keyboardType = KeyboardType.Text)

        Input(value = telefone.value, onValueChange = { telefone.value = it }, label = "Telefone", leadingIcon = Icons.Default.Phone, keyboardType = KeyboardType.Phone)

        Input(value = descricao.value, onValueChange = { descricao.value = it }, label = "Descrição", leadingIcon = Icons.Default.ThumbUp, keyboardType = KeyboardType.Text)

        Input(value = localizacao.value, onValueChange = { localizacao.value = it }, label = "Localização (cidade-uf)", leadingIcon = Icons.Default.Place, keyboardType = KeyboardType.Text)

        Input(value = disponibilidade.value, onValueChange = { disponibilidade.value = it }, label = "Disponibilidade (dias/semana)", leadingIcon = Icons.Default.DateRange, keyboardType = KeyboardType.Number)

//        Input(value = newItem.value, onValueChange = { newItem.value = it }, label = "Localização", leadingIcon = Icons.Default.LocationOn, keyboardType = KeyboardType.Text)

        SelectStyleInput(expanded = expanded1, selectedOption = selectedOption1, options = options1, leadingIcon = Icons.Default.Add)

        if (error.value === true) {
            Text(text = "Preencha todos os campos")
        }
        
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Input(fraction = 0.6f, value = newItem.value, onValueChange = { newItem.value = it }, label = "Seus interesses", leadingIcon = Icons.Default.Add, keyboardType = KeyboardType.Text)
            SubButton(onclick = {
                val newItemText = newItem.value.trim()
                if (newItemText.isNotBlank()) {
                    setItems(items.toMutableList().apply { add(newItemText) })
                    newItem.value = ""
                }}, text = "Adicionar", fraction = 1f)
        }

        Interests(items = items, setItems = setItems)

        CustomSpacer(space = 20.dp)

        MainButton(onclick = {
            if (name.value.isNotBlank() && telefone.value.isNotBlank() && descricao.value.isNotBlank() && items.isNotEmpty() && selectedOption1.value !== "Escolaridade" && localizacao.value.isNotBlank() && disponibilidade.value.isNotBlank()) {
                var user = User(
                    name = name.value, description = descricao.value, phone = telefone.value, degree =  selectedOption1.value, id = id, interests = items, seen = true, )
                var call = RetrofitFactory().userService().updateUser(id, user)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Log.i("consolelog", "onResponse: ${response.body()}")
                        navController.navigate(("main"))
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.i("consolelog", "Error", t)
                    }
                })
            } else {
                error.value = true
            }
        }, text = "Continuar")

        CustomSpacer(space = 70.dp)
    }
}

@Composable
fun Interests(items: MutableList<String>, setItems: (MutableList<String>) -> Unit) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        items.forEach { item ->
            WordButton(
                text = item,
                onRemove = { setItems(items.filter { it != item }.toMutableList()) }
            )
        }
    }
}

@Composable
fun WordButton(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            tint = Color.Gray,
            modifier = Modifier.clickable { onRemove() }
        )
    }
}