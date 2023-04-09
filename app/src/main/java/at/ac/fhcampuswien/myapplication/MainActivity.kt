package at.ac.fhcampuswien.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.ac.fhcampuswien.myapplication.navigation.Navigation
import at.ac.fhcampuswien.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
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