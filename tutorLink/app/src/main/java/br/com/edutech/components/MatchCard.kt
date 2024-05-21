package br.com.edutech.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.edutech.R
import br.com.edutech.model.User

@Composable
fun MatchCard(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .background(
                color = colorResource(id = R.color.beige),
                shape = RoundedCornerShape(16.dp)
            )
            .height(80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (user.name !== null && user.phone !== null) {
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = user.name
            )
            Text(
                fontSize = 16.sp,
                text = user.phone
            )
        }
    }
}