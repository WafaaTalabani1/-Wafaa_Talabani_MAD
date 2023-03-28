package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.MovieRow
import at.ac.fhcampuswien.myapplication.SimpleAppBar
import at.ac.fhcampuswien.myapplication.models.MovieViewModel

@Composable
fun FavoritesScreen(navController : NavController, viewModel: MovieViewModel){
    val favoritsMovies = viewModel.favoriteMoviesList

    Column {
        SimpleAppBar(title = "Favorites") {
            navController.popBackStack()
        }
        LazyColumn{
            items(favoritsMovies){ movie ->
                MovieRow(
                    movie = movie,
                    onItemClick = {},
                    onFavClick = {
                        viewModel.toggleFavorite(movie.id)
                    }
                )
            }
        }
    }
}