package br.com.edutech.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import br.com.edutech.R

@Composable
fun SubButton(onclick: () -> Unit, text: String, fraction: Float? = null) {
    var fractionContainer = 1f
    var fractionButton = 0.5f

    if (fraction != null) {
        fractionContainer = fraction
        fractionButton = 1f
    }

    Row(
        modifier = Modifier.fillMaxWidth(fractionContainer),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onclick,
            modifier = Modifier.fillMaxWidth(fractionButton),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.brown)
            ),
        ) {
            Text(
                text = text,
                color = colorResource(id = R.color.white)
            )
        }
    }
}