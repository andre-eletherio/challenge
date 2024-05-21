package br.com.edutech.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.edutech.R

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    fraction: Float? = null
) {
    val applyFraction = fraction ?: 1f
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxWidth(applyFraction),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "",
                tint = colorResource(id = R.color.brown),
                modifier = Modifier.padding(start = 22.dp, end = 5.dp)
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.brown)
        )
    )
}
