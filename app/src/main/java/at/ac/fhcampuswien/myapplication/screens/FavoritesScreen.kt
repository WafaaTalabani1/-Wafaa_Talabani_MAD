package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.utils.InjectorUtils
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.viewModels.FavoritesViewModel
import at.ac.fhcampuswien.myapplication.widgets.MovieRow
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar
import kotlinx.coroutines.launch


@Composable
fun FavoritesScreen(navController: NavController){
    val viewModel: FavoritesViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(
        LocalContext.current))
    val movieListState by viewModel.movieListState.collectAsState()
    val coroutinescope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            SimpleAppBar(title = "Favorites", navController = navController)
            LazyColumn (userScrollEnabled = true) {
                items(movieListState) { movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(Screen.DetailsScreen.route + "/$movieId")},
                        onFavoriteClick = {coroutinescope.launch {   viewModel.updateFavorite(it) }

                        }
                    )
                }
            }
        }
    }
}