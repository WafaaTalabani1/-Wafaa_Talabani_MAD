package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.utils.InjectorUtils
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.viewModels.DetailsViewModel
import at.ac.fhcampuswien.myapplication.widgets.ImageRow
import at.ac.fhcampuswien.myapplication.widgets.MovieRow
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(navController: NavController, movieId: Int?){
    val viewModel: DetailsViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(
        LocalContext.current))
    val movieListState by viewModel.movieListState.collectAsState()
    val coroutinescope = rememberCoroutineScope()
    val movie: Movie? = movieId?.let { movieListState.get(it) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        Column(Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            if (movie != null) {
                SimpleAppBar(title = movie.title, navController = navController)
            }
            if (movie != null) {
                MovieRow(
                    movie = movie,
                    onFavoriteClick = {
                        coroutinescope.launch {
                            viewModel.toggleIsFavorite(movie)
                        }

                    }
                )
            }
            movie?.images?.let { ImageRow(images = it, title = "Movie Images" ) }

        }
    }
}

