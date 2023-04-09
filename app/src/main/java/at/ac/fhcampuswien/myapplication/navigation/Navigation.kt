package at.ac.fhcampuswien.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.myapplication.models.Screen
import at.ac.fhcampuswien.myapplication.screens.AddMovieScreen
import at.ac.fhcampuswien.myapplication.screens.DetailScreen
import at.ac.fhcampuswien.myapplication.screens.FavoritesScreen
import at.ac.fhcampuswien.myapplication.screens.HomeScreen
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModel

@Composable
fun Navigation (){
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(
            route = Screen.HomeScreen.route
        ){
            HomeScreen(
                navController = navController,
                viewModel = movieViewModel
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
                viewModel = movieViewModel,
                movieId = backStackEntry.arguments?.getString("movieId")
            )
        }

        composable(
            route = Screen.FavoritesScreen.route
        ){
            FavoritesScreen(
                navController = navController,
                viewModel = movieViewModel
            )
        }

        composable(
            route = Screen.AddMovieScreen.route
        ){
            AddMovieScreen(
                navController = navController,
                viewModel = movieViewModel
            )
        }
    }
}