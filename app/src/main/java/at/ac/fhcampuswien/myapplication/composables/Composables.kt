package at.ac.fhcampuswien.myapplication.composables

import androidx.compose.animation.AnimatedVisibility

import androidx.compose.foundation.layout.*

import androidx.compose.material.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.myapplication.R


@Composable
fun RequiredFieldErrorMessage(msg: String, visible: Boolean){
    AnimatedVisibility(visible = visible) {
        Text(
            text = "$msg is required.",
            color = Color.Red,
            modifier = Modifier.padding(start = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun notValidMessage(msg: String){
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = "Please enter a valid movie $msg" ,
        color = Color.Red,
        fontSize = 12.sp
    )
}

@Composable
fun notValidGenreMessage(msg: String){
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = "Please select at least one $msg" ,
        color = Color.Red,
        fontSize = 12.sp
    )
}
