package at.ac.fhcampuswien.myapplication.models

sealed class Screen(val route: String){
    object HomeScreen:  Screen("home")
    object DetailsScreen: Screen("detailsScreen")
    object FavoritesScreen: Screen("favorites")
    object AddMovieScreen: Screen("addMovie")
}


