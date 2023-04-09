package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModel
import at.ac.fhcampuswien.myapplication.widgets.HomeAppBar
import at.ac.fhcampuswien.myapplication.widgets.MovieRow

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            HomeAppBar(
                onDropDownEdit = { navController.navigate(Screen.AddMovieScreen.route) },
                onDropDownFavorite = { navController.navigate(Screen.FavoritesScreen.route) }
            )
            LazyColumn (userScrollEnabled = true) {
                items(viewModel.movieList) { movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(Screen.DetailsScreen.route + "/$movieId") },
                        onFavoriteClick = { viewModel.toggleFavorite(it) }
                    )
                }
            }
        }
    }
}
