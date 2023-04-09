package at.ac.fhcampuswien.myapplication.composables

import androidx.compose.animation.AnimatedVisibility

import androidx.compose.foundation.layout.*

import androidx.compose.material.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.style.TextAlign


@Composable
fun ErrorMessage(msg: String, visible: Boolean){
    AnimatedVisibility(visible = visible) {
        Text(
            text = "$msg is required. Please try again.",
            color = Color.Red,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}