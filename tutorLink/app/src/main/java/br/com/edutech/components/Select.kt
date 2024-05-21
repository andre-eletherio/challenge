package br.com.edutech.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.edutech.R

@Composable
fun Select(expanded: MutableState<Boolean>, selectedOption: MutableState<String>, options: List<String>, value: MutableState<String?>) {

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { expanded.value = true },
            colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.beige)
                    ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(id = R.color.brown),
                text = selectedOption.value
            )
        }
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    value.value = option
                    selectedOption.value = option
                    expanded.value = false
                })
            }
        }
    }
}