package at.ac.fhcampuswien.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.movieapp.models.getMovies
import at.ac.fhcampuswien.myapplication.screens.DetailScreen
import at.ac.fhcampuswien.myapplication.screens.FavoritScreen

@Composable
/*
fun MyNavigation(){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home") { HomeScreen(navController)}
        composable("detailsScreen/{movieId}",
        arguments = listOf(navArgument("movieId"){
            type = NavType.StringType
        })
        ) { backStackEntry ->

            //val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            DetailScreen(navController, movie = getMovies()[5])}

    }
}

*/




fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home") { HomeScreen(navController)}
        composable("detailsScreen/{movieId}",
            arguments = listOf(navArgument("movieId"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val movie = getMovies().find { it.id == movieId } ?: getMovies()[0]
            DetailScreen(navController, movie = movie)
        }
        composable("favorites"){ FavoritScreen(navController = navController)}
    }
}