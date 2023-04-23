package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.utils.InjectorUtils
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.viewModels.HomeViewModel
import at.ac.fhcampuswien.myapplication.widgets.HomeAppBar
import at.ac.fhcampuswien.myapplication.widgets.MovieRow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel =
        viewModel(factory= InjectorUtils.provideMovieViewModelFactory(LocalContext.current))
    val allMovieList by homeViewModel.movie.collectAsState()

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
                items(allMovieList) { movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(Screen.DetailsScreen.route + "/$movieId") },
                        onFavoriteClick = {
                            coroutineScope.launch {
                                homeViewModel.toggleFavorite(it)
                            }
                        }
                    )
                }
            }
        }
    }
}