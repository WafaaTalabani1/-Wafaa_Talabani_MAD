package at.ac.fhcampuswien.myapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.movieapp.models.getMovies
import at.ac.fhcampuswien.myapplication.models.MovieViewModel
import at.ac.fhcampuswien.myapplication.screens.AddMovieScreen
import at.ac.fhcampuswien.myapplication.screens.DetailScreen
import at.ac.fhcampuswien.myapplication.screens.FavoritesScreen

@Composable
fun MyNavigation(){

    val viewModel: MovieViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreen.route){
        composable(HomeScreen.route) { HomeScreen(navController,viewModel)}
        composable(DetailsScreen.route.plus("/{movieId}"),
            arguments = listOf(navArgument("movieId"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val movie = getMovies().find { it.id == movieId } ?: getMovies()[0]
            DetailScreen(navController, movie = movie ,  viewModel.toggleFavorite(movieId))
        }
        composable(FavoritesScreen.route){ FavoritesScreen(navController = navController,viewModel)}
        composable(AddMovieScreen.route){AddMovieScreen(navController,viewModel)}
    }
}