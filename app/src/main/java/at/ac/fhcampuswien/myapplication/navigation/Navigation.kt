package at.ac.fhcampuswien.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.movieapp.models.getMovies
import at.ac.fhcampuswien.myapplication.screens.DetailScreen
import at.ac.fhcampuswien.myapplication.screens.DetailsScreen
import at.ac.fhcampuswien.myapplication.screens.FavoritesScreen
import at.ac.fhcampuswien.myapplication.screens.HomeScreen

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreen.route){
        composable(HomeScreen.route) { HomeScreen(navController)}
        composable(
            DetailsScreen.route.plus("/{movieId}"),
            arguments = listOf(navArgument("movieId"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val movie = getMovies().find { it.id == movieId } ?: getMovies()[0]
            DetailScreen(navController, movie = movie)
        }
        composable(FavoritesScreen.route){ FavoritesScreen(navController = navController)}
    }
}