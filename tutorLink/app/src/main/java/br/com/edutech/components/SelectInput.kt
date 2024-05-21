package br.com.edutech.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SelectInput(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = TextFieldValue(selectedItem),
            onValueChange = { /* Disable editing */ },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { { expanded = true } },
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { item }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}