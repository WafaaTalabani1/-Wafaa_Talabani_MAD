package at.ac.fhcampuswien.myapplication

import android.graphics.Paint.Align
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.movieapp.models.getMovies
import at.ac.fhcampuswien.myapplication.models.MovieViewModel
import at.ac.fhcampuswien.myapplication.ui.theme.MyApplicationTheme

import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Initialize and access ViewModel inside Activities (outside of composable scope)
         */
        val viewModel : MovieViewModel by viewModels()
        viewModel.movies
        setContent {
            MyNavigation()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "i am in onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "i am in onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "i am in onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "i am in onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "i am in onDestroy")
    }

    override fun onStart(){
        super.onStart()
        Log.i("MainActivity", "i am in onStart")
    }
    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("MyActivity", "onBackPressed")
    }


}



@Composable
fun Greeting(name: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var name by remember {
            mutableStateOf("")
        }
        Text(text = "Hello $name!")
        OutlinedTextField(value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}