package at.ac.fhcampuswien.myapplication

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.movieapp.models.getMovies
import at.ac.fhcampuswien.myapplication.ui.theme.MyApplicationTheme
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var dropDownMenuExpanded by remember {
                mutableStateOf(false)
            }

            Column {
                TopAppBar(
                    title = {
                        Text(text = "Movies")
                    },
                    backgroundColor = Color.Blue,
                    contentColor = Color.White,
                    elevation = 15.dp,
                    actions = {
                        IconButton(onClick = {
                            dropDownMenuExpanded = true
                        }) {
                            Icon(imageVector = Icons.Default.MoreVert, "")
                        }
                        DropdownMenu(
                            expanded = dropDownMenuExpanded,
                            onDismissRequest = { dropDownMenuExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DropdownMenuItem(onClick = { /*TODO*/ }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "Favorites")
                                }

                            }
                        }
                    }
                )
                MovieList()
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie = getMovies()[0]) {
    var expanded by remember {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.images[2]),
                    modifier = Modifier
                        .requiredSize(400.dp, 220.dp)
                        .fillMaxSize(),
                    contentDescription = null
                )
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to Favorite",
                    modifier = Modifier.padding(20.dp),
                    tint = Color.White
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.title, fontSize = 15.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight(10),
                    modifier = Modifier.padding(20.dp)
                )
                Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "keyboardArrowUp",
                    modifier = Modifier.clickable { expanded = !expanded })
            }

            AnimatedVisibility(visible = expanded,
                enter = slideInVertically { -it },
                exit = slideOutVertically { +it },
                modifier = Modifier.padding(10.dp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Director: ${movie.director}", modifier = Modifier.fillMaxWidth())
                    Text(text = "Released: ${movie.year}", modifier = Modifier.fillMaxWidth())
                    Text(text = "Genre: ${movie.genre}", modifier = Modifier.fillMaxWidth())
                    Text(text = "Actors: ${movie.actors}", modifier = Modifier.fillMaxWidth())
                    Text(text = "Rating: ${movie.rating}", modifier = Modifier.fillMaxWidth())
                    Text(
                        text = "------------------------------------",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = "Plot: ${movie.plot}", modifier = Modifier.fillMaxWidth())
                }

            }
        }

    }
}


@Composable
fun MovieList(movies: List<Movie> = getMovies()) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie)
        }
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