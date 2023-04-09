package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModel
import at.ac.fhcampuswien.myapplication.widgets.MovieRow
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar


@Composable
fun FavoritesScreen(navController: NavController, viewModel: MovieViewModel){
    val favoriteMovies: List<Movie> = viewModel.getFavoriteMovies()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            SimpleAppBar(title = "Favorites", navController = navController)
            LazyColumn (userScrollEnabled = true) {
                items(favoriteMovies) { movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(Screen.DetailsScreen.route + "/$movieId")},
                        onFavoriteClick = {
                            viewModel.toggleFavorite(it)
                        }
                    )
                }
            }
        }
    }
}