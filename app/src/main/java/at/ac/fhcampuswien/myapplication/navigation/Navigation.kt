package at.ac.fhcampuswien.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.models.getMovies
import at.ac.fhcampuswien.myapplication.screens.AddMovieScreen
import at.ac.fhcampuswien.myapplication.screens.DetailScreen
import at.ac.fhcampuswien.myapplication.screens.FavoritesScreen
import at.ac.fhcampuswien.myapplication.screens.HomeScreen

@Composable
fun Navigation (){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(
            route = Screen.HomeScreen.route
        ){
            HomeScreen(
                navController = navController
            )
        }

        composable(
            route= Screen.DetailsScreen.route +"/{movieId}",
            arguments = listOf(navArgument("movieId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            DetailScreen(
                navController = navController,
                movie = getMovies().get(1)
                //movieId = backStackEntry.arguments?.getInt("movieId")
            )
        }

        composable(
            route = Screen.FavoritesScreen.route
        ){
            FavoritesScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.AddMovieScreen.route
        ){
            AddMovieScreen(
                navController = navController
            )
        }
    }
}