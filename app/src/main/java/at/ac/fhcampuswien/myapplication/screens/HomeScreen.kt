package at.ac.fhcampuswien.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.myapplication.models.MovieViewModel
import coil.compose.rememberAsyncImagePainter




@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: MovieViewModel) {
    var dropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
    var expanded by remember {
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
                    onDismissRequest = { dropDownMenuExpanded = false }
                ) {
                    DropdownMenuItem(onClick = { navController.navigate("favorites")
                                               expanded = false }) {
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
                    DropdownMenuItem(onClick = { navController.navigate("addMovie")
                        expanded = false }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Add Movie")
                        }

                    }
                }
            }
        )

        MovieList(
            navController = navController,
            movies = viewModel.movies,
            onFavClick = { movieId -> viewModel.toggleFavorite(movieId) }
        )
    }

}

@Composable
fun MovieRow(
    movie: Movie,
    onFavClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    val isFavorite = remember { mutableStateOf(movie.isFavorite) }
    val viewModel: MovieViewModel = viewModel()
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.images[2]),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = {
                        isFavorite.value = !isFavorite.value
                        viewModel.toggleFavorite( movie.id )
                        onFavClick(movie.id)
                    }) {
                        Icon(
                            imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Add to Favorite",
                            tint = if (isFavorite.value) Color.Red else Color.Blue
                        )
                    }

                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(5.dp)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "keyboardArrowUp",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically { -it },
                exit = slideOutVertically { +it },
                modifier = Modifier.padding(10.dp)
            ) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Director: ${movie.director}")
                        Text(text = "Released: ${movie.year}")
                        Text(text = "Genre: ${movie.genre}")
                        Text(text = "Actors: ${movie.actors}")
                        Text(text = "Rating: ${movie.rating}")

                    }
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Plot: ${movie.plot}", textAlign = TextAlign.Start)
                    }
                }
            }

        }

    }
}


@Composable
fun MovieList(navController: NavController, movies: List<Movie>,onFavClick: (String) -> Unit ) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(
                    movie = movie,
                    onItemClick = { movieId ->
                    navController.navigate("detailsScreen/${movieId}"){
                        popUpTo("home")
                    }
                }, onFavClick = onFavClick
            )
        }

    }
}

@Composable
fun SimpleAppBar(
    title: String,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        title = { Text(title) }
    )
}


