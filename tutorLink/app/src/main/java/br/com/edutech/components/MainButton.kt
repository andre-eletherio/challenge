package br.com.edutech.components

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.edutech.R

@Composable
fun MainButton(onclick: () -> Unit, text: String) {
    ElevatedButton(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.brown)
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(300.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(id = R.color.white)
        )
    }
}