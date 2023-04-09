package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModel
import at.ac.fhcampuswien.myapplication.widgets.ImageRow
import at.ac.fhcampuswien.myapplication.widgets.MovieRow
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar


@Composable
fun DetailScreen(navController: NavController, viewModel: MovieViewModel, movieId: String?){
    val movie: Movie = viewModel.getMovieById(movieId)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        Column(Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            SimpleAppBar(title = movie.title, navController = navController)
            MovieRow(
                movie = movie,
                onFavoriteClick = {
                    viewModel.toggleFavorite(it)
                }
            )
            movie.images?.let { ImageRow(images = it, title = "Movie Images" ) }

        }
    }
}
