package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.myapplication.MovieRow
import at.ac.fhcampuswien.myapplication.SimpleAppBar
import at.ac.fhcampuswien.myapplication.models.MovieViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailScreen(navController: NavController, movie: Movie, viewModel: MovieViewModel) {
    Column {
        SimpleAppBar(title = movie.title) {
            navController.popBackStack()
        }

        MovieRow(
            movie = movie,
            onFavClick = { movieId ->
                viewModel.toggleFavorite(movieId)
            },
            onItemClick = {}
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Movie Images", color = Color.Black,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
            )
        }
        LazyRow {
            items(movie.images.size) {
                Card(
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(400.dp)
                        .padding(10.dp),
                    elevation = 7.dp
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.images[it]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
